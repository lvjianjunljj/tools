import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 * @author Jianjun Lv
 * @date 7/6/2018 4:52 PM
 */
public class MYSQLControl {
    //根据自己的数据库地址修改
//    static DataSource ds = MyDataSource.getDataSource("jdbc:mysql://127.0.0.1:3306/moviedata");
//    static QueryRunner qr = new QueryRunner(ds);

    //第一类方法
    public static void executeUpdate(String sql) {
//        try {
//            qr.update(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    //第二类数据库操作方法
    public static void executeInsert(List<GitSearchModel> repositoryData) throws SQLException {
        /*
         * 定义一个Object数组，行列
         * 3表示列数，根据自己的数据定义这里面的数字
         * params[i][0]等是对数组赋值，这里用到集合的get方法
         *
         */
        Object[][] params = new Object[repositoryData.size()][3];
        for (int i = 0; i < params.length; i++) {
            params[i][0] = repositoryData.get(i).getRepositoryName();
            params[i][1] = repositoryData.get(i).getRepositoryURL();
        }
//        qr.batch("insert into jingdongbook (bookID, bookName, bookPrice)"
//                + "values (?,?,?)", params);
        System.out.println("执行数据库完毕！" + "成功插入数据：" + repositoryData.size() + "条");

    }
}
