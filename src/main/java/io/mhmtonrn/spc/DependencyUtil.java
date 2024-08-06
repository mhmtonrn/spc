package io.mhmtonrn.spc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DependencyUtil {

    public static List<String> getPostgresDependency() {
        List<String> postgres = new ArrayList<>();
        postgres.add("\t\t\t<dependency>");
        postgres.add("\t\t\t\t<groupId>org.postgresql</groupId>");
        postgres.add("\t\t\t\t<artifactId>postgresql</artifactId>");
        postgres.add("\t\t\t\t<scope>runtime</scope>");
        postgres.add("\t\t\t</dependency>");
        return postgres;
    }
}
