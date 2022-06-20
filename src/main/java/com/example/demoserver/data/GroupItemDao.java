package com.example.demoserver.data;

import com.example.demoserver.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupItemDao {
    public static GroupItem save(GroupItem item){
        GroupItem out =  null;

        Connection connection= Database.getConnection();
        int status = 0;

        String query = "insert into groupItems(name,orgId) values " +
                "('"+item.getName()+"',"+item.getOrgId()+");";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            status = pstmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if(status==1){
                ResultSet keys = pstmt.getGeneratedKeys();
                keys.next();
                out =  get(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public static GroupItem saveWithItems(GroupItem groupItem){
        GroupItem out = null;

        List<Item> items =  new ArrayList<>();
        System.out.println(groupItem.toString());
        out = save(groupItem);

        for (Item item: groupItem.getItems()) {
            item.setGroupId(out.getId());
            item.setOrgId(out.getOrgId());
            items.add(ItemDao.saveWithGroup(item));
        }
        out.setItems(items);

        return out;
    }

    static public GroupItem get(int id){
        Connection connection = Database.getConnection();
        GroupItem out = null;
        String query = "select id, name, createdAt, orgId from groupItems where id ="+id+" limit  1;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                out = new GroupItem();
                out.setId(set.getInt("id"));
                out.setOrgId(set.getInt("orgId"));
                out.setName(set.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(out!=null){
            out.setItems(ItemDao.getAllWithGroupId(out.getId()));
        }
        return out;
    }

    static public GroupItem getAll(){
        Connection connection = Database.getConnection();
        GroupItem out = null;
        String query = "select id, name, createdAt, orgId from groupItems where id is not null ;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                out = new GroupItem();
                out.setId(set.getInt("id"));
                out.setName(set.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public static int delete(int id){
        int status=0;
        Connection connection = Database.getConnection();
        String query = "delete from groupItems where id="+id+" ;";
        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;

    }

    public static int deleteWithItems(int id){
        int status=0;
        Connection connection = Database.getConnection();

        ItemDao.deleteWithGroupId(id);

        String query = "delete from groupItems where id="+id+" ;";
        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;

    }



}
