package task;

import java.util.HashMap;
import java.util.List;

public interface TaskDao {
	List<HashMap<?, ?>> selectUserTask(int uid);
	int selectUidCountByUserTask(int uid);
}
