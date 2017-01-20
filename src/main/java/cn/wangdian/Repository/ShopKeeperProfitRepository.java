package cn.wangdian.Repository;

import cn.wangdian.Model.ShopAttributes;
import cn.wangdian.Model.ShopKeeperProfit;
import cn.wangdian.Model.YuMing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 25065 on 2016/9/18.
 */
public interface ShopKeeperProfitRepository extends JpaRepository<ShopKeeperProfit,String>,JpaSpecificationExecutor<ShopKeeperProfit> {


    @Modifying
    @Query("update ShopKeeperProfit o set o.isDel=1 where lower(o.id)=lower(:id) ")
    public void deleteByPrimaryKey(@Param("id") Integer id);

    @Modifying
    @Query("update ShopKeeperProfit o set o.status=1 where lower(o.id)=lower(:id) ")
    public void updateStatusOnOkByPrimaryKey(@Param("id") Integer id);

    @Modifying
    @Query("update ShopKeeperProfit o set o.status=2 where lower(o.id)=lower(:id) ")
    public void updateStatusOnRejectByPrimaryKey(@Param("id") Integer id);

    @Query("select o from ShopKeeperProfit o where lower(o.id)=lower(:id)")
    public ShopKeeperProfit findById(@Param("id") Integer id);
}
