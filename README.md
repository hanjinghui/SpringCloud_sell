 **1、feign调用超时设置** 

```
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 6000 #设置hystrix超时时间,单位ms
```


```
feign:
  client:
    config:
      default:
        connectTimeout: 10000
        readTimeout: 10000
```

