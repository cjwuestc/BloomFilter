package com.github.cjwuestc.BloomFilter;

/**
 * 布隆过滤器
 * 负责存储已访问过的记录，记录形式目前主要是字符串形式
 * 不存储记录本身，只存储记录的“信息指纹”
 * 信息指纹，度娘~
 * @author Administrator
 *
 */
public interface BloomFilter {
	
	/**
	 * 添加一条记录
	 * @param info 信息记录
	 * @return true 添加成功
	 * @return false 添加失败，当且仅当记录已存在
	 */
	public boolean add(String info);
	
	/**
	 * 记录是否存在
	 * @param info 信息记录
	 * @return true 记录存在
	 * @return false 记录不存在
	 */
	public boolean exist(String info);
	
	/**
	 * 能够存储的记录个数
	 * @return 记录个数
	 */
	public int getBloomSize();
	
	/**
	 * 扩咱存储的记录个数
	 * @param infoLength 扩展的记录个数
	 * @return true 扩展成功
	 * @return false 扩展失败，当且仅当扩展的长度比当前记录的长度要小
	 */
	public boolean extendsBloomSize(int size);
	
	/**
	 * 获取已记录信息的个数
	 * @return 已记录的信息的个数
	 */
	public int getInfoNum();
}
