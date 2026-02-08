import com.fullcycle.admin.catalogo.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-integration")
@SpringBootTest(classes = WebServerConfig.class)
@ComponentScan(
        basePackages = "com.fullcycle.admin.catalogo.infrastructure",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*[MySQLGateway]")
        }
)
@ExtendWith(CleanUpExtensions.class)
@Tag("integrationTest")
public @interface IntegrationTest {
}
