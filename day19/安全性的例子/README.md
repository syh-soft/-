数据加密和解密：

   ```
 public static String encryption(String s){
        s = "2020"+s;
        return s;
    }
```


```
    public  static String decrypt(String s){
        return s.substring(4);
    }
```



访问数据库权限的确认：

```
@PostMapping("/getMessage")
    public String getMessage(@RequestBody UserEntity userEntity){
        if(userEntity.getName().equals("root")){
            return userService.queryById(1).toString();
        }
        else{
            return "你没有权限查询数据";
        }
    }
```



token验证：
定时改变token：

```
@Component
@Configuration
@EnableScheduling
public class Tack {

    @Scheduled(cron = "0/60 * * * * ?")
    public void test(){
        Token.token += 1;
        System.out.println(Token.token);
    }
}
```




验证token：


```
 @PostMapping("/judge")
    public String judge(@RequestBody UserEntity userEntity){
        int token = Integer.parseInt(userEntity.getPhone());
        System.out.println(token);
        if(token!= Token.token){
            int i = Token.token;
            return "token错误，正确的token为"+String.valueOf(i);
        }
        else{
            return "恭喜token正确";
        }
    }
```

    


