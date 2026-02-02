package org.example.web.annotation;

import users.ClientRoles;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //this makes the annotation available via reflection
@Target({ElementType.METHOD, ElementType.TYPE}) //this specifies where we can place the annotations
public @interface JwtRolesAllowed {
    ClientRoles[] value();

}
