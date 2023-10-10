# Mail service

Description: <br>
1. ### Create Google account
- Click to <button> Merge your Google Account </button> -> <button> Security </button>
-  You should have 2-Step Verification
- Click to <button> App password </button> and create new one
- Firstly enter App name like: "SpringBootDemoApp" -> enter <button> Generate </button>
- Secondary copy App password and save it smw
2. ### Config your application.properties file:
## properties configuration
```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=<login user to smtp server>
spring.mail.password=<login password to smtp server>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```



