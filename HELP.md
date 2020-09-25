# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/#build-image)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#howto-use-exposing-spring-data-repositories-rest-endpoint)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-security)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Validation](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-validation)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Sécurité
Guideline pour gérer la sécurité dans Spring Boot

* Créer une classe de configuration (@Configuration) qui vient définir comment la sécurité va être gérée
* Elle doit étendre WebSecurityConfigurerAdapter et surcharger 2 méthodes (configure(HttpSecurity) et configure(AuthenticationManagerBuilder)
* configure(HttpSecurity) --> la configuration de la sécurité
* configure(AuthenticationManagerBuilder)  --> charger les utilisateurs du système
* Utilisateur -> soit inMemory (dev et test rapides) soit gestion complète entitié User implements UserDetails + service + contrôleur sécurisé
* Penser au CORS avec la création d'un Bean CorsConfigurationSource corsConfigurationSource()
* Si vous utiliser un gestionnaire d'exception personnalisées, penser à définir le comportement des 401 (authenticationEntryPoint), des 403 (@ExceptionHandler(AccessDeniedException.class)), des 404 avec les deux clés dans application.yaml +  @ExceptionHandler(NoHandlerFoundException.class)
####Possibilités de sécurisation
* Directement dans HttpSecurity avec la définition des .antMatchers(verbe HTTP, ...urlPaterns).has(Any)Authority(...Authorities)
* Via les méthodes ou class avec @PreAuthorize("hasAuthority('authority')) pour protéger les méthodes ou services (pour effectuer cette approche, il faut aussi créer une classe de configuration qui va étendre GlobalMethodSecurityConfiguration)
