# Blake2b
Blake2b工具类
  
接入
      1.repositories {
          flatDir {
            dirs 'libs'
        }
       }
     2.implementation(name: 'blake2b-release', ext: 'aar')

使用方法
  
   * input 输入的字节数组
   * outLen 输出的字节数组长度
  
   byte[] output =  Blake2bUtil.blake2b(byte[] input,int outLen);
  具体看demo示例
  
  
