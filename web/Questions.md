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

---
# How would you troubleshoot a 404 Not Found error?
A 404 Not Found error indicates that the requested resource could not be found on the server. Troubleshooting steps include:
 - Verifying the URL for typos.
 - Checking if the resource exists on the server.
 - Reviewing server logs for more details.
 - Ensuring the server is correctly configured to serve the requested resource.
# Explain what a 500 Internal Server Error means and how to resolve it.
A 500 Internal Server Error indicates that the server encountered an unexpected condition that prevented it from fulfilling the request. Resolving it may involve:
 - Checking server logs for specific error messages.
 - Reviewing recent changes to the server configuration or code.
 - Ensuring that server resources (CPU, memory) are not exhausted.
 - Validating server scripts and dependencies.
# What are the security implications of using HTTP instead of HTTPS?
Using HTTP exposes data to potential interception and tampering by attackers, as it is transmitted in plain text. HTTPS encrypts the data, providing confidentiality, integrity, and authentication, thereby mitigating these risks.
# How can HTTP caching improve web performance?
HTTP caching reduces the need to repeatedly fetch resources from the server by storing copies of responses on the client or intermediate proxies. This reduces latency, decreases server load, and speeds up page load times.
# What are HTTP headers, and why are they important?
HTTP headers are key-value pairs sent in HTTP requests and responses. They provide essential information about the request or response, such as content type, content length, server details, and caching directives, allowing for more efficient and secure communication.

---
# What is the purpose of the Host header in HTTP/1.1?
The `Host` header specifies the domain name of the server (and optionally the port number) to which the request is being sent. It is required in HTTP/1.1 to support virtual hosting, where multiple domains are hosted on a single IP address.
# What is the `Referer` header used for?
The `Referer` header indicates the URL of the previous web page from which a request was made. It can be used for analytics, logging, and improving user experience by understanding navigation paths.
# Describe the purpose of the User-Agent header.
The `User-Agent` header contains information about the client software (e.g., browser, operating system) making the request. This information can be used for content negotiation, logging, and optimizing responses based on client capabilities.
# What is HTTP/3 and what improvements does it bring?
HTTP/3 is the latest version of the HTTP protocol, built on the QUIC transport layer protocol. It improves performance by reducing latency, providing faster connection establishment, and enhancing error correction mechanisms.
# What are HTTP cookies, and how are they set?
HTTP cookies are small pieces of data stored on the client side and used to maintain stateful information. They are set using the `Set-Cookie` header in the server's response and sent back to the server with subsequent requests via the `Cookie` header.
