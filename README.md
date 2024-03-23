# Spring Security Basic Authentication

## Important classes:
* ``SpringBootWebSecurityConfiguration``
    The default configuration for web security. It relies on Spring Security's
    content-negotiation strategy to determine what sort of authentication to use. If
    the user specifies their own bean, this will back-off
    completely and the users should specify all the bits that they want to configure as
    part of the custom security configuration.
    
* 