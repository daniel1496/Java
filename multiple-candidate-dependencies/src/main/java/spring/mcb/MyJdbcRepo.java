package spring.mcb;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("db")
public class MyJdbcRepo implements MyRepo {
}
