# Watch Your Profamity
A simple profanity filter. Returns whether the submitted string contains profanity or not.

## Prerequisites
Java
Maven
MongoDB

### Installing and starting MongoDB
```
brew install mongodb
sudo mkdir -p /data/db
sudo chown -R `id -u` /data/db
mongod
```


## Usage
To run execute: `mvn spring-boot:run` from the project root. By default it will attempt to run on port 8082.

Example usage will check whether the string 'foo' contains profanity:
```
GET /profanity/check?text=foo 
```

## Swagger Docs
While running the API, you can access the swagger documentation at:
```
/swagger-ui.html
```