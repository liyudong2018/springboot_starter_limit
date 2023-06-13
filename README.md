# springboot_starter_limit
A Spring Boot starter that limits speed.
配置 
sprigboot: application.yml
limit-config:
     list:
         -
           name: t1
           size: 10000
           speed: 10
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
