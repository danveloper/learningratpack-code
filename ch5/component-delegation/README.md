Will successfully show the user:

```
curl -H "x-auth-token: 0123ratpack456" -v localhost:5050/users/danveloper
```

Will recieve an unauthorized response:

```
curl -H "x-auth-token: xxx" -v localhost:5050/users/danveloper
```
