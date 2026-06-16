import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(BookStoreTest.class)
@IncludeTags("delete")
public class DeleteSuite {
}
