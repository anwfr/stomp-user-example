# stomp-user-example
Example of using spring websocket + STOMP for sending messages to specific recipients, with integration tests.

This project illustrates:
- assigning a random username as principal to each connecting client (see AssignPrincipalHandshakeHandler)
- usage of messagingTemplate.convertAndSendToUser for targeting a specific client
- usage of messagingTemplate.convertAndSend for broadcasting to all clients
- STOMP integration tests (see WsConfigIntegrationTest)

## Expected behavior
- A client calls /register (with a custom message)
- This client receives to /private/reply: "Thanks for your registration!"
- All connected clients receive to /queue: "Someone just registered saying: {custom message}"

## Run instructions
```
cd stomp-user-example
./gradlew test
```

## Result
```
2017-12-25 20:20:23.695  INFO 30760 --- [o-auto-1-exec-1] f.a.s.server.config.WsConfig             : ===> handleConnectEvent: username=ehaJpt8nbPfqCtg5qyDA7vZmBynGvfZa, event=SessionConnectEvent[GenericMessage [payload=byte[0], headers={simpMessageType=CONNECT, stompCommand=CONNECT, nativeHeaders={heart-beat=[0,0], accept-version=[1.1,1.2]}, simpSessionAttributes={__principal__=ehaJpt8nbPfqCtg5qyDA7vZmBynGvfZa}, simpHeartbeat=[J@4c48918e, simpUser=fr.anw.stompUserExample.server.config.AssignPrincipalHandshakeHandler$1@42bfa46f, simpSessionId=0}]]
2017-12-25 20:20:23.722  INFO 30760 --- [lient-AsyncIO-1] f.a.s.server.ClientSessionHandler        : afterConnected: username=ehaJpt8nbPfqCtg5qyDA7vZmBynGvfZa
2017-12-25 20:20:23.736  INFO 30760 --- [o-auto-1-exec-3] f.a.s.server.config.WsConfig             : ===> handleConnectEvent: username=Ma6SUukht5MVi55JPIjIKABT5vnRS9xZ, event=SessionConnectEvent[GenericMessage [payload=byte[0], headers={simpMessageType=CONNECT, stompCommand=CONNECT, nativeHeaders={heart-beat=[0,0], accept-version=[1.1,1.2]}, simpSessionAttributes={__principal__=Ma6SUukht5MVi55JPIjIKABT5vnRS9xZ}, simpHeartbeat=[J@151cdaea, simpUser=fr.anw.stompUserExample.server.config.AssignPrincipalHandshakeHandler$1@501e562d, simpSessionId=2}]]
2017-12-25 20:20:23.748  INFO 30760 --- [lient-AsyncIO-1] f.a.s.server.ClientSessionHandler        : afterConnected: username=Ma6SUukht5MVi55JPIjIKABT5vnRS9xZ
2017-12-25 20:20:23.748  INFO 30760 --- [           main] f.a.s.server.WsConfigIntegrationTest     : ### client1 subscribes
2017-12-25 20:20:23.759  INFO 30760 --- [o-auto-1-exec-4] f.a.s.server.config.WsConfig             : <==> handleSubscribeEvent: username=ehaJpt8nbPfqCtg5qyDA7vZmBynGvfZa, event=SessionSubscribeEvent[GenericMessage [payload=byte[0], headers={simpMessageType=SUBSCRIBE, stompCommand=SUBSCRIBE, nativeHeaders={destination=[/queue], id=[0]}, simpSessionAttributes={__principal__=ehaJpt8nbPfqCtg5qyDA7vZmBynGvfZa}, simpHeartbeat=[J@29fb94ac, simpSubscriptionId=0, simpUser=fr.anw.stompUserExample.server.config.AssignPrincipalHandshakeHandler$1@42bfa46f, simpSessionId=0, simpDestination=/queue}]]
2017-12-25 20:20:23.767  INFO 30760 --- [o-auto-1-exec-4] f.a.s.server.config.WsConfig             : <==> handleSubscribeEvent: username=ehaJpt8nbPfqCtg5qyDA7vZmBynGvfZa, event=SessionSubscribeEvent[GenericMessage [payload=byte[0], headers={simpMessageType=SUBSCRIBE, stompCommand=SUBSCRIBE, nativeHeaders={destination=[/private/reply], id=[1]}, simpSessionAttributes={__principal__=ehaJpt8nbPfqCtg5qyDA7vZmBynGvfZa}, simpHeartbeat=[J@5d5f6505, simpSubscriptionId=1, simpUser=fr.anw.stompUserExample.server.config.AssignPrincipalHandshakeHandler$1@42bfa46f, simpSessionId=0, simpDestination=/private/reply}]]
2017-12-25 20:20:23.857  INFO 30760 --- [           main] f.a.s.server.WsConfigIntegrationTest     : ### client2 subscribes
2017-12-25 20:20:23.859  INFO 30760 --- [o-auto-1-exec-5] f.a.s.server.config.WsConfig             : <==> handleSubscribeEvent: username=Ma6SUukht5MVi55JPIjIKABT5vnRS9xZ, event=SessionSubscribeEvent[GenericMessage [payload=byte[0], headers={simpMessageType=SUBSCRIBE, stompCommand=SUBSCRIBE, nativeHeaders={destination=[/queue], id=[0]}, simpSessionAttributes={__principal__=Ma6SUukht5MVi55JPIjIKABT5vnRS9xZ}, simpHeartbeat=[J@4b6d7636, simpSubscriptionId=0, simpUser=fr.anw.stompUserExample.server.config.AssignPrincipalHandshakeHandler$1@501e562d, simpSessionId=2, simpDestination=/queue}]]
2017-12-25 20:20:23.899  INFO 30760 --- [o-auto-1-exec-6] f.a.s.server.config.WsConfig             : <==> handleSubscribeEvent: username=Ma6SUukht5MVi55JPIjIKABT5vnRS9xZ, event=SessionSubscribeEvent[GenericMessage [payload=byte[0], headers={simpMessageType=SUBSCRIBE, stompCommand=SUBSCRIBE, nativeHeaders={destination=[/private/reply], id=[1]}, simpSessionAttributes={__principal__=Ma6SUukht5MVi55JPIjIKABT5vnRS9xZ}, simpHeartbeat=[J@3eeb93d6, simpSubscriptionId=1, simpUser=fr.anw.stompUserExample.server.config.AssignPrincipalHandshakeHandler$1@501e562d, simpSessionId=2, simpDestination=/private/reply}]]
2017-12-25 20:20:23.960  INFO 30760 --- [           main] f.a.s.server.WsConfigIntegrationTest     : ### client1 registers
2017-12-25 20:20:23.989  INFO 30760 --- [nboundChannel-4] f.a.s.s.controllers.RegisterController   : new registration: username=ehaJpt8nbPfqCtg5qyDA7vZmBynGvfZa, payload=hello guys
2017-12-25 20:20:24.004  INFO 30760 --- [lient-AsyncIO-2] f.a.s.server.WsConfigIntegrationTest     : --> /private/reply (cli1) : Thanks for your registration!
2017-12-25 20:20:24.005  INFO 30760 --- [lient-AsyncIO-2] f.a.s.server.WsConfigIntegrationTest     : --> /queue (cli1) : Someone just registered saying: hello guys
2017-12-25 20:20:24.010  INFO 30760 --- [lient-AsyncIO-1] f.a.s.server.WsConfigIntegrationTest     : --> /queue (cli2) : Someone just registered saying: hello guys
2017-12-25 20:20:24.166  INFO 30760 --- [           main] f.a.s.server.WsConfigIntegrationTest     : ### client2 registers
2017-12-25 20:20:24.167  INFO 30760 --- [nboundChannel-1] f.a.s.s.controllers.RegisterController   : new registration: username=Ma6SUukht5MVi55JPIjIKABT5vnRS9xZ, payload=yo!
2017-12-25 20:20:24.168  INFO 30760 --- [lient-AsyncIO-1] f.a.s.server.WsConfigIntegrationTest     : --> /queue (cli1) : Someone just registered saying: yo!
2017-12-25 20:20:24.169  INFO 30760 --- [lient-AsyncIO-2] f.a.s.server.WsConfigIntegrationTest     : --> /private/reply (cli2) : Thanks for your registration!
2017-12-25 20:20:24.169  INFO 30760 --- [lient-AsyncIO-2] f.a.s.server.WsConfigIntegrationTest     : --> /queue (cli2) : Someone just registered saying: yo!
2017-12-25 20:20:24.278  INFO 30760 --- [o-auto-1-exec-9] f.a.s.server.config.WsConfig             : <=== handleDisconnectEvent: username=ehaJpt8nbPfqCtg5qyDA7vZmBynGvfZa, event=SessionDisconnectEvent[sessionId=0, CloseStatus[code=1000, reason=null]]
2017-12-25 20:20:24.280  INFO 30760 --- [tboundChannel-1] f.a.s.server.config.WsConfig             : <=== handleDisconnectEvent: username=Ma6SUukht5MVi55JPIjIKABT5vnRS9xZ, event=SessionDisconnectEvent[sessionId=2, CloseStatus[code=1002, reason=null]]
```