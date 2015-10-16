package com.github.cjwuestc.BloomFilter;

public class BloomFilterTest {
	
	public void bloomTest() {
		String info1 = "baidu";
		String info2 = "ahri";
		String info3 = "tecent";
		String info4 = "uestc";
		String info5 = "uestc2";
		String info6 = "uestc3";
		
		
		StringBloomFilter filter = new StringBloomFilter(3);
		System.out.println("----------------------添加一个字符串-------------------------");
		System.out.println("添加字符串" + info1 + ":" + (filter.add(info1)? "成功":"失败"));
		System.out.println("重复添加:" + (filter.add(info1)? "成功":"失败"));
		
		System.out.println("添加字符串" + info2 + ":" + (filter.add(info2)? "成功":"失败"));
		System.out.println("添加字符串" + info3 + ":" + (filter.add(info3)? "成功":"失败"));

		//filter.add(info2);
		//filter.add(info4);
		
		System.out.println("--------------------查询一个字符串是否已添加----------------------");
		System.out.println("查询字符串" + info1 + ":" + (filter.exist(info1)? "存在":"不存在"));
		System.out.println("查询字符串" + info2 + ":" + (filter.exist(info2)? "存在":"不存在"));
		System.out.println("查询字符串" + info3 + ":" + (filter.exist(info3)? "存在":"不存在"));
		

		System.out.println("--------------------添加查过允许添加的上限-----------------------");
		System.out.println("添加字符串" + info4 + ":" + (filter.add(info4)? "成功":"失败"));
		
		System.out.println("------------------------扩展测试----------------------------");
		System.out.println(filter.extendsBloomSize(4)? "扩展成功" : "扩展失败");
		System.out.println("当前容量:" + filter.bloomCapacity());
		System.out.println("添加字符串" + info4 + ":" + (filter.add(info4)? "成功":"失败"));
		
		/**
		for(int i=0 ; i<20 ;i++)
		    System.out.println("添加字符串" + info4 + i + ":" + (filter.testAdd(info2+i)? "成功":"失败"));	
		*/
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BloomFilterTest().bloomTest();
	}

}
