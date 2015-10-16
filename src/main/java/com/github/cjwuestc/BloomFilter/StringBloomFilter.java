/**
 * 
 */
package com.github.cjwuestc.BloomFilter;

import java.util.BitSet;
import java.util.Random;

/**
 * @author Administrator
 *
 */
public class StringBloomFilter implements BloomFilter {
	public static final int INFO_FINGERPRINT_LENGTH = 8;
	
	private BitSet bits;
	private BitPerItem bitPerItem = BitPerItem.NORMAL;
	
	private int bloomCapacity;

	private int[] seeds = new int[INFO_FINGERPRINT_LENGTH];
	private int numOfInfo = 0;
	
	private FingerprintGenerator[] generator = new FingerprintGenerator[INFO_FINGERPRINT_LENGTH];
	
	/**
	 * 构造器
	 * @param bloomSize 能够存储的字符串的记录数
	 * @param seeds 随机数种子数组
	 */
	public StringBloomFilter(int bloomSize, int[] seeds) {
		if(seeds.length != INFO_FINGERPRINT_LENGTH){
			for(int i=0 ; i<INFO_FINGERPRINT_LENGTH ; i++)
				this.seeds[i] = seeds[i];
		}
		else
			this.seeds = seeds;

		creatBits(bloomSize);
		for(int i=0 ; i<seeds.length ; i++){
			generator[i] = new FingerprintGenerator(bits.size(), seeds[i]);
		}
			
	}
	
	public StringBloomFilter(int bloomSize) {
		int[] seeds = new int[INFO_FINGERPRINT_LENGTH];
		
		for(int i=0 ; i<seeds.length ; i++)
			seeds[i] = i;
		
		creatBits(bloomSize);
		for(int i=0 ; i<seeds.length ; i++){
			generator[i] = new FingerprintGenerator(bits.size(), seeds[i]);
		}
			
	}
	
	/**
	 * 枚举每个消息记录的bit数目
	 * NORMAL 出错率为0.1%
	 * HIGHT 出错率近乎为0%
	 * @author Administrator
	 *
	 */
	public static enum BitPerItem {
		NORMAL(4),
		HIGHT(8);
		
		private int ratio;
		
		BitPerItem(int ratio) {
			this.ratio = ratio;
		}
		
		public int getRatio() {
			return this.ratio;
		}
	}
	
	/**
	 * 信息指纹发生器
	 * @author Administrator
	 *
	 */
	private class FingerprintGenerator {
		private int cap;
		private int seed; //生成信息指纹的随机数种子
		
		public FingerprintGenerator(int cap, int seed) {
			this.cap = cap;
			this.seed = seed;
		}
		
		/**
		 * 生成信息指纹
		 * @param info 信息
		 * @return 信息指纹
		 */
		public int getFingerprint(String info) {
			int result = 0;
			int len = info.length();
			for(int i=0 ; i<len ; i++){
				result = result*seed + info.charAt(i);
			}
			return (int)result % (int)cap;
			
		}
	}
	
	/**
	 * 添加信息
	 */
	public boolean add(String str) {
		if(exist(str) || (numOfInfo+1)>bloomCapacity())
			return false;
		
		for(int i=0 ; i<INFO_FINGERPRINT_LENGTH ; i++) {
			int index = generator[i].getFingerprint(str);
			bits.set(index, true);
		}
		numOfInfo ++;
		return true;
	}
	
	public boolean testAdd(String str) {
		if(exist(str))
			return false;
		
		for(int i=0 ; i<INFO_FINGERPRINT_LENGTH ; i++) {
			int index = generator[i].getFingerprint(str);
			bits.set(index, true);
		}
		numOfInfo ++;
		return true;
	}
	/**
	 * 信息是否存在
	 */
	public boolean exist(String str) {
		if(str == null)
			return false;
		
		boolean result = true;
		boolean fingerprint;
		for(FingerprintGenerator generat : generator) {
			fingerprint = bits.get(generat.getFingerprint(str));
			result = result && fingerprint;
		}
		
		return result;
	}

	/**
	 * 获取信息容量
	 * 更新方法名为bloomCapacity()
	 */
	@Deprecated
	public int getBloomSize() {
		// TODO Auto-generated method stub
		return bloomCapacity();
	}

	/**
	 * 扩展信息容量
	 */
	public boolean extendsBloomSize(int size) {
		// TODO Auto-generated method stub
		if(size < bloomCapacity())
			return false;
		
		creatBits(size);
		return true;
	}

	/**
	 * 获取已记录信息的个数
	 */
	public int getInfoNum() {
		// TODO Auto-generated method stub
		return this.numOfInfo;
	}
	
	/**
	 * 创建一个bits
	 * 如果bits不存在，新建一个bits，全为false
	 * 如果bits存在，则将向高位扩展，扩展位为false，低位保留原纪录
	 * @param size 可创建的记录数
	 */
	private void creatBits(int size) {
		this.bloomCapacity = size;
		
		if(this.bits == null)
			this.bits = new BitSet(size*bitPerItem.getRatio());
		else{
			BitSet temp = this.bits;
			this.bits = new BitSet(size*bitPerItem.getRatio());
			this.bits.or(temp);
		}			
	}
	
	/**
	 * 获取记录容器的容量
	 * @return
	 */
	public int bloomCapacity() {
		return this.bloomCapacity;
	}

}
