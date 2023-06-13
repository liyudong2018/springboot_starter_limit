# springboot_starter_limit
A Spring Boot starter that limits speed.  
一个用于限制速度的实现，可以实现对不同的队列进行限制速度，用ratelimiter实现.



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
     
     limitService.addData("t1",testData);   

4.


      获取对象   
      BaseData data = limitService.getData("t1");
    
    
 
 
  
