# What is HTTP?
HTTP (HyperText Transfer Protocol) is the foundational protocol used by the World Wide Web. It defines how messages are formatted and transmitted, and how web servers and browsers should respond to various commands.
# What is the default port used by HTTP and HTTPS?
HTTP uses port 80 by default, while HTTPS (HTTP Secure) uses port 443.
# Explain the difference between HTTP and HTTPS.
HTTP is the standard protocol for transferring web pages on the internet. HTTPS is the secure version of HTTP, which uses SSL/TLS to encrypt the data transmitted between the client and server, ensuring data integrity and privacy.
# What are HTTP methods? Name a few.
HTTP methods define the action to be performed on a resource. Common methods include:
 GET: Retrieve data from the server.
 POST: Send data to the server.
 PUT: Update existing data on the server.
 DELETE: Remove data from the server.
 HEAD: Retrieve headers only.
 OPTIONS: Discover the HTTP methods that the server supports.
# What is an HTTP status code?
HTTP status codes are issued by a server in response to a client's request made to the server. They indicate whether a specific HTTP request has been successfully completed.
---
# What is a persistent connection in HTTP?
Persistent connections, also known as keep-alive connections, allow multiple requests and responses to be sent over a single TCP connection, reducing the overhead of establishing new connections for each request.
# What is the difference between GET and POST methods?
  GET: Requests data from a specified resource. Parameters are appended to the URL and visible in the URL string. Suitable for idempotent operations.
  POST: Submits data to be processed to a specified resource. Data is included in the body of the request. Suitable for non-idempotent operations.
# Explain what a cookie is and its purpose.
A cookie is a small piece of data sent from a server and stored on a client's browser. Cookies are used to remember information about the user, such as login status, preferences, and tracking identifiers.
# What is a session in HTTP?
A session is a way to store information on the server side to track user interactions with a web application across multiple requests. Sessions are often maintained using cookies containing session identifiers.
# What is CORS (Cross-Origin Resource Sharing)?
CORS is a security feature implemented by web browsers to prevent web pages from making requests to a different domain than the one that served the web page. It allows servers to specify who can access their resources and how.
---
# What is HTTP/2 and how does it improve upon HTTP/1.1?
HTTP/2 is a major revision of the HTTP protocol that improves performance by allowing multiple concurrent exchanges on the same connection (multiplexing), compressing headers, and reducing latency through improved request and response prioritization.
# Explain the concept of idempotency in HTTP methods.
Idempotency refers to the property of certain HTTP methods where multiple identical requests have the same effect as a single request. For example, GET, PUT, and DELETE are idempotent methods, while POST is not.
# What is an HTTP proxy and how does it work?
An HTTP proxy acts as an intermediary between a client and a server. It can be used for various purposes, such as caching, filtering requests, and improving security. When a client sends a request, the proxy processes it and forwards it to the server, then returns the server's response to the client.
# What are WebSockets and how do they differ from HTTP?
WebSockets provide a full-duplex communication channel over a single TCP connection. Unlike HTTP, which is a request-response protocol, WebSockets allow for real-time communication between a client and server with lower latency and overhead.
# What is a RESTful API?
A RESTful API is an API that adheres to the principles of REST (Representational State Transfer). It uses standard HTTP methods, is stateless, and typically communicates using JSON or XML. RESTful APIs are designed to be simple, scalable, and easily accessible.
