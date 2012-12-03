What is it ?
===

Robusta Web Library is a non-intrusive productivity library for RESTful projects, with special conveniance for GWT.

<div style="color:red">
Warning : This is Alpha state. Do not use in Production. Nevertheless, stable and tested Beta is coming very soon. I'll need people to propose the main features before RC release.
</div>


Why REST ?
===

RESTful web services allows communication between entities such as browser, Http servers or mobile phone. Robusta is written in Java but, because of REST, can talk with entities written in any other languages.
![ ](http://lh5.ggpht.com/_D-Y4QMlvJ98/TS8euEapcLI/AAAAAAAAA1w/MqQv4GfuCQY/descriptions.png)

Robusta's Contents
===

The library contains :

* Http Clients, enabling easy and uniform RESTful requests
* A simple Resource interface for Domain Objects, with an id, representation and uri
* A Representation interface for Resources, or even any Class
* Xml and Json Representation implementations based on Jdom, JsonSimple, Gson
    * Or extends it to the technology you want
* JAX-RS ResourceController, that receives and handles Http requests
* Utilities and productivity classes

These five parts are independants! So you can create and handle REST requests with your own legacy domain classes. Or use productivity classes, such as XmlRepresentation? or CoupleList?<L,R>, for a non REST project.

A Framework ? Spilling out of the frame is OK
===

There is very loose Coupling between different Robusta's concepts. You can add Robusta to an existing project, and you won't be locked after a few years.

![ ](http://lh4.ggpht.com/_D-Y4QMlvJ98/TS8RUvAWpSI/AAAAAAAAA1o/xEAaToEMLP0/robustaUML.png&nonsense=something.png)

This diagram means that you can use Representation without using Robusta's Resource, ResourceController? without using Robusta's Representation. Pick what you need !

Simple creation of a Resource Representation
===

Let's say we have :

    User johnDoe = new User(1L, "john.doe@robustaweb.com", "John", "Doe");

where User implements the `Resource` interface.

`johnDoe.getRepresentation().toString()` will return :

      <user>
        <id>1</id>
        <email>john.doe@robustaweb.com</email>
        <firstname>John</firstname>
        <lastname>Doe</lastname>
      </user>

This is often enough to get informations on the precious concept of User. But you still make easy changes with :

    Representation representation = johnDoe.getRepresentation();
    representation.set("username", representation.get("email"));
    System.out.println( representation );

This adds the email as username in the Xml representation :

      <user>
        <id>1</id>
        <email>john.doe@robustaweb.com</email>
        <firstname>John</firstname>
        <lastname>Doe</lastname>
        <username>john.doe@robustaweb.com</username>
      </user>


Using the Client
====

Let's say we have a Client built from the JDK :

    Client client = new JdkClient("http://www.myapp.com");

There are at many ways to use a Client :


 * Build a Representation : `Representation userRepresentation = client.GET("/user/12", parameters);`
 * Map an object : `User user = client.GET ("/user/12", parameters, User.class);`
 * Get the raw String : `String str = client.GET(url, parameters, String.class);`
 * Fetch a Stream : `InputStream stream = client.GETStream ("/user/12", parameters);`
 * Use any of previous method asynchronously : `client.GET("/user/12", parameters, myCallback);`
 * Use a `Apache HttpClient`, a `GwtClient` or a `FutureClient`
 * Extend any of these clients to build yours

Note that when you map the User Resource, the returned object does not need to implement the Resource interface.