# Directory Structure
``` 
project
├── env
    └── mysql.env (mysql 도커 환경변수)
├── flyway 
    ├── conf
    	└── flyway_main.conf (migration 관련 flyway 설정내용)
    └── db-migration
    	├── main
            ├── migration 대상 sql 파일들
            └── ...
└── docker-compose.yml (docker compose 설정)
```

## Run Docker Compose
```
$ docker-compose up --build
```

