package cn.wangdian.Controller.Admin.Admin;

import cn.wangdian.Model.Orders;
import cn.wangdian.Model.ShopKeeper;
import cn.wangdian.Model.ShopKeeperProfit;
import cn.wangdian.Service.OrdersService;
import cn.wangdian.Service.ShopKeeperProfitService;
import cn.wangdian.Service.ShopKeeperService;
import cn.wangdian.utils.ExecuteResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 25065 on 2016/9/17.
 */
@Controller
@RequestMapping("/admin")
public class ProfitController {

    private ExecuteResult executeResult=new ExecuteResult();

    private Log logger= LogFactory.getLog(ProfitController.class);

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ShopKeeperProfitService shopKeeperProfitService;
    @Autowired
    protected ShopKeeperService shopKeeperService;

    private static int parameterCountBefore=0;

    @RequestMapping("/profit/list")
    public String ordersList(Model model,
                             String shopType,String shopName,String shopModel,String shopNumber,
                             String shopKeeper,String startTime,String endTime){
        try {
            if (shopType!=null&&!shopType.equals("")){
                model.addAttribute("shopType",shopType);
            }
            if (shopName!=null&&!shopName.equals("")){
                model.addAttribute("shopName",shopName);
            }
            if (shopKeeper!=null&&!shopKeeper.equals("")){
                model.addAttribute("shopKeeper",shopKeeper);
            }
            if (shopModel!=null&&!shopModel.equals("")){
                model.addAttribute("shopModel",shopModel);
            }
            if (shopNumber!=null&&!shopNumber.equals("")){
                model.addAttribute("shopNumber",shopNumber);
            }
            if (startTime!=null&&!startTime.equals("")){
                model.addAttribute("startTime",startTime);
            }

            if (endTime!=null&&!endTime.equals("")){
                model.addAttribute("endTime",endTime);
            }

            long tiJiaDingDanCount = ordersService.countAllByIsDel0(shopType,shopName,shopModel,shopNumber,shopKeeper,startTime,endTime,0);
            model.addAttribute("tiJiaDingDanCount",tiJiaDingDanCount);

            long yiFuKuanWeiFaHuoCount=ordersService.countAllByIsDel0(shopType,shopName,shopModel,shopNumber,shopKeeper,startTime,endTime,1);
            model.addAttribute("yiFuKuanWeiFaHuoCount",yiFuKuanWeiFaHuoCount);

            long yiFuKuanYiFaHuoCount=ordersService.countAllByIsDel0(shopType,shopName,shopModel,shopNumber,shopKeeper,startTime,endTime,2);
            model.addAttribute("yiFuKuanYiFaHuoCount",yiFuKuanYiFaHuoCount);

            long yiQueShouHuoCount=ordersService.countAllByIsDel0(shopType,shopName,shopModel,shopNumber,shopKeeper,startTime,endTime,3);
            model.addAttribute("yiQueShouHuoCount",yiQueShouHuoCount);

            long shenQingTuiHuoZhongCount=ordersService.countAllByIsDel0(shopType,shopName,shopModel,shopNumber,shopKeeper,startTime,endTime,4);
            model.addAttribute("shenQingTuiHuoZhongCount",shenQingTuiHuoZhongCount);

            long yiKuanCount=ordersService.countAllByIsDel0(shopType,shopName,shopModel,shopNumber,shopKeeper,startTime,endTime,5);
            model.addAttribute("yiKuanCount",yiKuanCount);

//            long shopTypeCount=ordersService.countShopType(shopType,shopName,shopModel,shopNumber,shopKeeper,startTime,endTime);
//            model.addAttribute("shopTypeCount",shopTypeCount);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "backPage/admin/profit/list";
    }

    @RequestMapping("/profit/tiXian")
    public String tiXian(Model model,
                         String orderField,String orderDirection,Integer pageSize,Integer pageCurrent,
                         String shopKeeper,String tiXianTime,Integer status){

        Page<ShopKeeperProfit> shopKeeperProfitsList= null;

        try {
            int parameterCountNow=0;
            if (shopKeeper!=null&&!shopKeeper.equals("")){
                model.addAttribute("shopKeeper",shopKeeper);
                parameterCountNow++;
            }
            if (tiXianTime!=null&&!tiXianTime.equals("")){
                model.addAttribute("tiXianTime",tiXianTime);
                parameterCountNow++;
            }
            if (status!=null&&!status.equals("")){
                model.addAttribute("status",status);
                parameterCountNow++;
            }


            if (pageSize==null||pageSize.equals("")){
                pageSize=5;
            }
            //有多少页
            int count=shopKeeperProfitService.countAllByIsDel0(shopKeeper,tiXianTime,status);
            int pageNumbers=0;
            if (count%pageSize==0){
                //整除
                pageNumbers=count/pageSize;
            }else {
                //有余数
                pageNumbers=count/pageSize+1;
            }

            if (pageCurrent==null||pageCurrent.equals("")){
                pageCurrent=0;
            }else if (parameterCountNow!=parameterCountBefore){
                pageCurrent=0;
                parameterCountBefore=parameterCountNow;
            }else if (pageCurrent>pageNumbers){
                pageCurrent=0;
            }else {
                pageCurrent=pageCurrent-1;
            }

            PageRequest pageRequest=new PageRequest(pageCurrent,pageSize);
            shopKeeperProfitsList = shopKeeperProfitService.findAllByIsDel0(shopKeeper,tiXianTime,status,orderField,orderDirection,pageRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("shopKeeperProfitsList",shopKeeperProfitsList);
        return "backPage/admin/profit/tiXianList";
    }

    /**
     * ok
     * @return
     */
    @ResponseBody
    @RequestMapping("/profit/ok")
    public Object ok(Integer id,float money){
        try {
            ShopKeeperProfit shopKeeperProfit=shopKeeperProfitService.findById(id);

            Integer status=shopKeeperProfit.getStatus();
            if (status==0){
                //尚未批准
                shopKeeperProfitService.updateStatusOnOkByPrimaryKey(id);
                ShopKeeper shopKeeper1=shopKeeperService.selectByUsername(shopKeeperProfit.getShopKeeper());

                String message=shopKeeperProfit.getShopKeeper()+"于"+shopKeeperProfit.tiXianTimeToString()+"申请提现"+shopKeeperProfit.getMoney()+"元,已被批准";
                float yiTiXian=shopKeeper1.getYiTiXian()+money;

                shopKeeperService.updateMessageAndYiTiXianById(message,yiTiXian,shopKeeper1.getId());

            }else if (status==1){
                //已批准
                return executeResult.jsonReturn(300,"你已被批准，不能重复批准",false);
            }else if (status==2){
                //已拒绝，改为已申请，需要加未加的钱
                shopKeeperProfitService.updateStatusOnOkByPrimaryKey(id);
                ShopKeeper shopKeeper1=shopKeeperService.selectByUsername(shopKeeperProfit.getShopKeeper());
                String message=shopKeeperProfit.getShopKeeper()+"于"+shopKeeperProfit.tiXianTimeToString()+"申请提现"+shopKeeperProfit.getMoney()+"元,已由拒绝改为批准";
                float yiTiXian=shopKeeper1.getYiTiXian()+money;

                shopKeeperService.updateMessageAndYiTiXianById(message,yiTiXian,shopKeeper1.getId());
            }

            return executeResult.jsonReturn(200,false);
        } catch (Exception e) {
            return executeResult.jsonReturn(300,e.getMessage(),false);
        }
    }

    /**
     * ok
     * @return
     */
    @ResponseBody
    @RequestMapping("/profit/reject")
    public Object reject(Integer id,float money){
        try {

            ShopKeeperProfit shopKeeperProfit=shopKeeperProfitService.findById(id);

            Integer status=shopKeeperProfit.getStatus();
            if (status==0){
                //尚未批准
                shopKeeperProfitService.updateStatusOnRejectByPrimaryKey(id);
                ShopKeeper shopKeeper1=shopKeeperService.selectByUsername(shopKeeperProfit.getShopKeeper());

                String message=shopKeeperProfit.getShopKeeper()+"于"+shopKeeperProfit.tiXianTimeToString()+"申请提现"+shopKeeperProfit.getMoney()+"元,已被拒绝";

                shopKeeperService.updateMessageById(message,shopKeeper1.getId());
            }else if (status==1){
                //已批准，由批准改为拒绝，需要减已加的钱
                shopKeeperProfitService.updateStatusOnRejectByPrimaryKey(id);
                ShopKeeper shopKeeper1=shopKeeperService.selectByUsername(shopKeeperProfit.getShopKeeper());

                String message=shopKeeperProfit.getShopKeeper()+"于"+shopKeeperProfit.tiXianTimeToString()+"申请提现"+shopKeeperProfit.getMoney()+"元,已由批准改为拒绝";
                float yiTiXian=shopKeeper1.getYiTiXian()-money;

                shopKeeperService.updateMessageAndYiTiXianById(message,yiTiXian,shopKeeper1.getId());

            }else if (status==2){
                //已拒绝
                return executeResult.jsonReturn(300,"你已被拒绝，不能重复拒绝",false);
            }

            return executeResult.jsonReturn(200,false);
        } catch (Exception e) {
            return executeResult.jsonReturn(300,e.getMessage(),false);
        }
    }

    /**
     * 删除
     * @return
     */
    @ResponseBody
    @RequestMapping("/profit/delete")
    public Object delete(Integer id){
        try {
            shopKeeperProfitService.deleteByPrimaryKey(id);
            return executeResult.jsonReturn(200,false);
        } catch (Exception e) {
            return executeResult.jsonReturn(300,e.getMessage(),false);
        }
    }
}
