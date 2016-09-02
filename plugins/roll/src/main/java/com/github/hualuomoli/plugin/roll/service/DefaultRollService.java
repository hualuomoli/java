package com.github.hualuomoli.plugin.roll.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.commons.util.RandomUtils;
import com.github.hualuomoli.commons.util.SerializeUtils;
import com.github.hualuomoli.plugin.roll.ByteRoll;
import com.github.hualuomoli.plugin.roll.Roll;
import com.github.hualuomoli.plugin.roll.SerializableRoll;
import com.github.hualuomoli.plugin.roll.StringRoll;
import com.github.hualuomoli.plugin.roll.base.entity.BasePollData;
import com.github.hualuomoli.plugin.roll.base.entity.BasePollFrequency;
import com.github.hualuomoli.plugin.roll.base.service.BasePollDataService;
import com.github.hualuomoli.plugin.roll.base.service.BasePollErrorHisService;
import com.github.hualuomoli.plugin.roll.base.service.BasePollFrequencyService;
import com.github.hualuomoli.plugin.roll.base.service.BasePollSuccessHisService;
import com.github.hualuomoli.plugin.roll.entity.PollData;
import com.github.hualuomoli.plugin.roll.entity.PollErrorHis;
import com.github.hualuomoli.plugin.roll.entity.PollFrequency;
import com.github.hualuomoli.plugin.roll.entity.PollSuccessHis;
import com.github.hualuomoli.plugin.roll.mapper.DefaultRollMapper;
import com.google.common.collect.Lists;

@Service(value = "com.github.hualuomoli.plugin.roll.service.DefaultRollService")
@Transactional(readOnly = true)
public class DefaultRollService implements Roll, StringRoll, ByteRoll, SerializableRoll, InitializingBean, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(DefaultRollService.class);

	// 默认值
	private static final String UN_LOCK_STRING = "UNLOCK";
	private static final String DEFAULT_FREQUENCY = "30s,1m,2m,5m,10m,20m,30m"; // 调度
	private static final Integer PRIORITY = 1; // 优先级
	private static final Integer lockMinute = 2; // 锁定数据时间,单位为分钟

	private static final Integer poolSize = 100; // 队列最大值
	private static final Integer theadSize = 10; // 线程个数
	private static final Integer heartSecond = 1; // 心跳休眠时间,单位为秒

	private ThradPoll<PollData> thradPoll = new ThradPoll<PollData>(theadSize, poolSize, heartSecond);

	@Autowired
	private BasePollDataService basePollDataService;
	@Autowired
	private BasePollFrequencyService basePollFrequencyService;
	@Autowired
	private BasePollSuccessHisService basePollSuccessHisService;
	@Autowired
	private BasePollErrorHisService basePollErrorHisService;
	@Autowired
	private DefaultRollMapper defaultRollMapper;

	private ApplicationContext applicationContext;

	@Override
	@Transactional(readOnly = false)
	public <T extends StringDealer> void pushString(String data, Class<T> dealer) {
		this.pushString(data, dealer, DEFAULT_FREQUENCY);
	}

	@Override
	@Transactional(readOnly = false)
	public <T extends StringDealer> void pushString(String data, Class<T> dealer, String frequency) {
		this.pushString(data, dealer, frequency, PRIORITY);
	}

	// 数据
	@Override
	@Transactional(readOnly = false)
	public <T extends StringDealer> void pushString(String data, Class<T> dealer, String frequency, Integer priority) {
		PollData pollData = new PollData();
		pollData.setDataType(PollData.DATA_TYPE_STRING);
		pollData.setStringData(data);

		this.save(pollData, dealer, frequency, priority);
	}

	@Override
	@Transactional(readOnly = false)
	public <T extends ByteDealer> void pushBytes(byte[] data, Class<T> dealer) {
		this.pushBytes(data, dealer, DEFAULT_FREQUENCY);
	}

	@Override
	@Transactional(readOnly = false)
	public <T extends ByteDealer> void pushBytes(byte[] data, Class<T> dealer, String frequency) {
		this.pushBytes(data, dealer, frequency, PRIORITY);
	}

	// 数据
	@Override
	@Transactional(readOnly = false)
	public <T extends ByteDealer> void pushBytes(byte[] data, Class<T> dealer, String frequency, Integer priority) {
		PollData pollData = new PollData();
		pollData.setDataType(PollData.DATA_TYPE_BYTES);
		pollData.setByteData(data);

		this.save(pollData, dealer, frequency, priority);
	}

	@Override
	@Transactional(readOnly = false)
	public <T extends SerializableDealer> void pushSerializable(Serializable data, Class<T> dealer) {
		this.pushSerializable(data, dealer, DEFAULT_FREQUENCY);
	}

	@Override
	@Transactional(readOnly = false)
	public <T extends SerializableDealer> void pushSerializable(Serializable data, Class<T> dealer, String frequency) {
		this.pushSerializable(data, dealer, frequency, PRIORITY);
	}

	// 数据
	@Override
	@Transactional(readOnly = false)
	public <T extends SerializableDealer> void pushSerializable(Serializable data, Class<T> dealer, String frequency, Integer priority) {
		PollData pollData = new PollData();
		pollData.setDataType(PollData.DATA_TYPE_SERIAL);
		pollData.setByteData(SerializeUtils.serialize(data));

		this.save(pollData, dealer, frequency, priority);

	}

	// 保存
	@Transactional(readOnly = false)
	private void save(PollData pollData, Class<?> dealer, String frequency, Integer priority) {
		String dataId = this.getDataId();

		// save data
		pollData.setDataId(dataId);
		pollData.setDealerClassName(dealer.getName());
		basePollDataService.insert(pollData);

		// save frequency
		PollFrequency pollFrequency = new PollFrequency();
		pollFrequency.setDataId(dataId);
		pollFrequency.setFrequency(frequency);
		pollFrequency.setRemainFrequency(frequency);
		pollFrequency.setExecuteTime(new Date()); // 执行时间为当前时间
		pollFrequency.setPriority(priority);
		pollFrequency.setLockString(UN_LOCK_STRING); // 未锁定
		pollFrequency.setUnLockTime(new Date()); // 解锁时间为当前时间
		basePollFrequencyService.insert(pollFrequency);

		// 唤醒
		new Thread(new Runnable() {
			@Override
			public void run() {
				thradPoll.wake();
			}
		}).start();

	}

	// 数据ID
	private String getDataId() {
		return RandomUtils.getUUID();
	}

	////////////////////////////////////////////////////
	// 处理业务逻辑
	private void deal(PollData pollData) {

		if (logger.isDebugEnabled()) {
			logger.debug("deal data {}", pollData.getId());
		}

		// 处理
		Boolean success = false;

		Object dealer = this.getDealer(pollData.getDealerClassName());
		// 没有处理者
		if (dealer == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("there is no dealer for {}", pollData.getDealerClassName());
			}
			this.end(pollData);
			return;
		}

		// 处理
		if (pollData.getDataType() == PollData.DATA_TYPE_STRING) {
			// string
			String data = pollData.getStringData();
			StringDealer stringDealer = (StringDealer) dealer;
			success = stringDealer.deal(data);
		} else if (pollData.getDataType() == PollData.DATA_TYPE_BYTES) {
			// byte[]
			byte[] data = pollData.getByteData();
			ByteDealer byteDealer = (ByteDealer) dealer;
			success = byteDealer.deal(data);
		} else if (pollData.getDataType() == PollData.DATA_TYPE_SERIAL) {
			// Serializable
			byte[] data = pollData.getByteData();
			Serializable serializable = SerializeUtils.unserialize(data);
			SerializableDealer serializableDealer = (SerializableDealer) dealer;
			success = serializableDealer.deal(serializable);
		} else {
			// 无法识别
			if (logger.isWarnEnabled()) {
				logger.warn("invalid data type", pollData.getDataType());
			}
			this.end(pollData);
			return;
		}

		// 修改数据状态
		if (success) {
			// 处理成功
			this.success(pollData);
		} else {
			// 处理异常,下次轮询
			this.error(pollData);
		}

	}

	// 获取处理器
	protected Object getDealer(String className) {
		Object dealer = null;

		dealer = this.getDealerFromSpring(className);
		if (dealer == null) {
			dealer = this.getDealerFromInstance(className);
		}

		return dealer;

	}

	// get from spring
	private Object getDealerFromSpring(String className) {
		try {
			return this.applicationContext.getBean(Class.forName(className));
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("{}", e.getMessage());
			}
		}
		return null;
	}

	// get from instance
	private Object getDealerFromInstance(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("{}", e.getMessage());
			}
		}
		return null;
	}

	// 处理成功
	@Transactional(readOnly = false)
	private void success(PollData pollData) {

		if (logger.isInfoEnabled()) {
			logger.info("通知成功{}", pollData.getDataId());
		}

		// 获取调度
		PollFrequency pollFrequency = basePollFrequencyService.getUnique(pollData.getDataId());

		// move to his
		PollSuccessHis pollSuccessHis = new PollSuccessHis();
		pollSuccessHis.setDataId(pollData.getDataId());
		pollSuccessHis.setDataType(pollData.getDataType());
		pollSuccessHis.setStringData(pollData.getStringData());
		pollSuccessHis.setByteData(pollData.getByteData());
		pollSuccessHis.setDealerClassName(pollData.getDealerClassName());
		pollSuccessHis.setFrequency(pollFrequency.getFrequency());
		pollSuccessHis.setPriority(pollFrequency.getPriority());
		basePollSuccessHisService.insert(pollSuccessHis);

		// delete
		basePollDataService.delete(pollData.getId());
		basePollFrequencyService.delete(pollFrequency.getId());
	}

	// 处理失败
	@Transactional(readOnly = false)
	private void error(PollData pollData) {
		// 获取调度
		PollFrequency pollFrequency = basePollFrequencyService.getUnique(pollData.getDataId());
		if (StringUtils.isBlank(pollFrequency.getRemainFrequency())) {
			this.end(pollData);
			return;
		}

		// last poll

		String remainFrequency = pollFrequency.getRemainFrequency();
		String[] array = remainFrequency.split("[,]");
		if (array.length == 1) {
			// 剩余一次执行
			remainFrequency = "";
		} else {
			remainFrequency = remainFrequency.substring(remainFrequency.indexOf(",") + 1);
		}
		Date executeTime = this.getLastDate(array[0]);
		if (logger.isDebugEnabled()) {
			logger.debug("通知失败{},等待下次通知,下次通知时间{}", pollData.getDataId(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(executeTime));
		}
		// 修改剩余调度
		PollFrequency pf = new PollFrequency();
		pf.setId(pollFrequency.getId());
		pf.setRemainFrequency(remainFrequency);
		pf.setLockString(UN_LOCK_STRING);
		pf.setExecuteTime(executeTime);
		basePollFrequencyService.update(pf);
	}

	// 结束轮询
	@Transactional(readOnly = false)
	private void end(PollData pollData) {

		if (logger.isInfoEnabled()) {
			logger.info("通知结束{}", pollData.getDataId());
		}

		// 获取调度
		PollFrequency pollFrequency = basePollFrequencyService.getUnique(pollData.getDataId());

		// move to his
		PollErrorHis pollErrorHis = new PollErrorHis();
		pollErrorHis.setDataId(pollData.getDataId());
		pollErrorHis.setDataType(pollData.getDataType());
		pollErrorHis.setStringData(pollData.getStringData());
		pollErrorHis.setByteData(pollData.getByteData());
		pollErrorHis.setDealerClassName(pollData.getDealerClassName());
		pollErrorHis.setFrequency(pollFrequency.getFrequency());
		pollErrorHis.setPriority(pollFrequency.getPriority());
		basePollErrorHisService.insert(pollErrorHis);

		// delete
		basePollDataService.delete(pollData.getId());
		basePollFrequencyService.delete(pollFrequency.getId());

	}

	// 下次的时间
	private Date getLastDate(String frequency) {
		Long next = 0L;
		if (frequency.endsWith("s")) {
			next = Integer.parseInt(frequency.substring(0, frequency.length() - 1)) * 1000L;
		} else if (frequency.endsWith("m")) {
			next = Integer.parseInt(frequency.substring(0, frequency.length() - 1)) * 60 * 1000L;
		} else if (frequency.endsWith("h")) {
			next = Integer.parseInt(frequency.substring(0, frequency.length() - 1)) * 60 * 60 * 1000L;
		} else if (frequency.endsWith("d")) {
			next = Integer.parseInt(frequency.substring(0, frequency.length() - 1)) * 24 * 60 * 60 * 1000L;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("invalid frequency {}", frequency);
			}
			next = 30 * 24 * 60 * 60 * 1000L;
		}
		return new Date(System.currentTimeMillis() + next);
	}

	// 添加新数据到队列
	@Transactional(readOnly = false)
	private List<PollData> loadData() {

		if (logger.isInfoEnabled()) {
			logger.info("loading data.......");
		}

		List<PollData> retList = Lists.newArrayList();

		// 锁定数据
		String lockString = RandomUtils.getUUID();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, lockMinute); // 锁定
		int updates = defaultRollMapper.lock(UN_LOCK_STRING, lockString, new Date(), calendar.getTime(), poolSize / 2);
		if (updates == 0) {
			return retList;
		}
		// get lock datas
		BasePollFrequency basePollFrequency = new BasePollFrequency();
		basePollFrequency.setLockString(lockString);
		List<BasePollFrequency> pfList = basePollFrequencyService.findList(basePollFrequency);
		if (pfList == null || pfList.size() == 0) {
			return retList;
		}
		ArrayList<Object> dataIds = Lists.newArrayList();
		for (BasePollFrequency bpf : pfList) {
			dataIds.add(bpf.getDataId());
		}
		BasePollData basePollData = new BasePollData();
		basePollData.setDataIdArray(dataIds.toArray(new String[] {}));
		List<BasePollData> dataList = basePollDataService.findList(basePollData);
		if (dataList == null || dataList.size() == 0) {
			return retList;
		}

		retList = Lists.newArrayList();
		for (BasePollData bpd : dataList) {
			retList.add(bpd);
		}
		return retList;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		final DefaultRollService service = this;

		thradPoll.init(new ThradPoll.Dealer<PollData>() {

			@Override
			public void deal(PollData t) {
				service.deal(t);
			}

			@Override
			public List<PollData> load() {
				return service.loadData();
			}
		});
	}

	public static class ThradPoll<T> {
		private Stack<T> queue;
		private Integer threadSize;
		private Integer count;
		private Integer heart;
		// 锁
		private Lock lock = new ReentrantLock();
		private Condition notEmptyCondition = lock.newCondition(); // 数据不为空
		private Condition loadNewCondition = lock.newCondition(); // 需要加载新数据
		private Condition heartCondition = lock.newCondition(); // 心跳检测

		private boolean hasNew; // 是否有新数据

		public ThradPoll(Integer threadSize, Integer loadedCount, Integer heartSecond) {
			this.threadSize = threadSize;
			this.count = loadedCount;
			this.heart = heartSecond;
			this.queue = new Stack<T>();
			hasNew = true; // 默认有新数据
		}

		// 初始化
		public void init(final Dealer<T> dealer) {

			// 处理着
			for (int i = 0; i < threadSize; i++) {
				new Thread(new Runnable() {

					@Override
					public void run() {

						while (true) {

							// 数据
							T t = null;

							// 处理前锁定
							lock.lock();

							// 防止内部错误,无法释放锁
							try {
								// 获取数据
								t = this.pop();

							} finally {
								// 释放锁
								lock.unlock();
							}

							// 处理(有处理时间,先释放锁再处理)
							this.deal(t);

							// end
						}
					}

					// 获取数据
					private T pop() {
						// 如果没有数据,当前线程挂起等待
						while (queue.isEmpty()) {
							if (logger.isDebugEnabled()) {
								logger.debug("{} waiting......", this.hashCode());
							}
							try {
								// 有数据时被唤醒
								notEmptyCondition.await();
							} catch (InterruptedException e) {
							}
						}

						// 获取需要处理的数据
						T t = queue.pop();

						// 唤醒加载新数据
						loadNewCondition.signal();

						return t;
					}

					// 处理数据
					private void deal(T t) {
						if (t == null) {
							return;
						}

						try {
							dealer.deal(t);
						} catch (Exception e) {
							if (logger.isWarnEnabled()) {
								logger.warn("{}", e.getMessage());
							}
						}
					}

				}).start();
			}

			// 加载者
			new Thread(new Runnable() {

				@Override
				public void run() {

					while (true) {

						// 获取数据
						List<T> list = this.load();

						// 处理前锁定
						lock.lock();

						// 防止内部错误,无法释放锁
						try {

							// 添加新数据
							this.push(list);

						} finally {
							// 释放锁
							lock.unlock();
						}
					}

				}

				private void push(List<T> list) {

					// 不需要加载新数据或者没有新数据
					if (list == null || list.size() == 0) {

						try {
							// 需要加载新数据时被唤醒
							loadNewCondition.await();
						} catch (InterruptedException e) {
						}

						System.out.println("\n\n\n\\n\n>>>>>>>>>>>>>\n\n\n\n\n\n\n");

						// 没有需要处理的数据
						hasNew = false;

						// 唤醒心跳检测线程
						heartCondition.signal();
					}

					// 添加到队列
					for (T t : list) {
						queue.push(t);
					}

					// 唤醒处理线程
					notEmptyCondition.signalAll();
				}

				// 加载新数据
				private List<T> load() {

					// 没有使用该应用添加数据,添加数据/心跳检测时会修改该值
					if (!hasNew) {
						return null;
					}

					// 如果数据个数未达到加载新数据的阈值,当前线程挂起等待
					if (queue.size() - count > 0) {
						if (logger.isDebugEnabled()) {
							logger.debug("数据量个数{},无需从数据库加载", queue.size());
						}
						return null;
					}

					// 加载新数据
					try {
						List<T> list = dealer.load();
						if (logger.isInfoEnabled()) {
							logger.info("load data {}, queueu {}", list.size(), queue.size());
						}
						return list;
					} catch (Exception e) {
						if (logger.isWarnEnabled()) {
							logger.warn("{}", e.getMessage());
						}
					}
					return null;
				}

			}).start();

			// 心跳检测
			// 每隔一秒,心跳检测是否有需要通知的数据
			new Thread(new Runnable() {

				private boolean init = false;

				@Override
				public void run() {
					while (true) {

						// 休眠等待
						this.sleep();

						// 有新数据,等待下次验证
						if (hasNew) {
							continue;
						}

						// 处理前锁定
						lock.lock();

						// 防止内部错误,无法释放锁
						try {

							try {
								// 等待
								heartCondition.await();
							} catch (InterruptedException e) {
							}

							// 有新数据
							hasNew = true;

							// 唤醒加载新数据
							loadNewCondition.signal();

						} finally {
							// 释放锁
							lock.unlock();
						}

					}
				}

				// 休眠等待
				private void sleep() {

					// 如果没有初始化,等待初始化完成后在进行休眠,防止线程等待影响应用启动
					if (!init) {
						init = true;
						return;
					}

					// 每几秒心跳一次
					if (logger.isDebugEnabled()) {
						logger.debug("heart waiting....");
					}
					try {
						Thread.sleep(1000 * heart);
					} catch (InterruptedException e) {
					}
				}

			}).start();

		}

		// 唤醒,加载新数据
		public void wake() {
			lock.lock();
			hasNew = true;
			loadNewCondition.signal();
			lock.unlock();
		}

		public static interface Dealer<T> {

			void deal(T t);

			List<T> load();
		}

	}

}
