# springboot_starter_limit
A Spring Boot starter that limits speed.  
一个用于限制速度的实现，可以实现对不同的队列进行限制速度，用ratelimiter实现.   

比如,有两个队列，A,B,A限速10,B限速5.总共15.   
当A队列放入速度超过每秒10个时，则按照速度10/S.   
如果没有超过10,则按照实际速度。    
如果B队列放入速度超过5,且 A队列放入速度没有超过10,则A队列会处理B队列数据   


所有队列遵循以上规则



配置  

springboot: application.yml  

limit-config:  

     list:  
     
         -
           name: t1(队列名称)  
           
           size: 10000(队列容量)  
           
           speed: 10（队列速度）   
           
         -
            name: t2
            size: 1000
            speed: 5
 编译后，在目标工程中引入   
 
  <dependency>  
     
     <groupId>com.wodhome.spring</groupId>  
     
    <artifactId>limit-starter</artifactId>  
     
    <version>0.0.1-SNAPSHOT</version>  
     
  </dependency>  
 使用方式:    
 
 1.  
    要放入对象实现BaseData，空接口    
  
 2.
 
          @Autowired    


         private LimitService limitService;    




3.

     
     放入队列     
     
     limitService.addData("t1",testData);   (阻塞放入，如果队列满，则阻塞)

4.


      获取对象   
      BaseData data = limitService.getData("t1")（如果取不到值，阻塞）;
    
    
 
 
  
