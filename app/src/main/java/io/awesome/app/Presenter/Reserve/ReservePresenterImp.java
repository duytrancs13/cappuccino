package io.awesome.app.Presenter.Reserve;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.awesome.app.View.Reserve.ReserveView;

/**
 * Created by sung on 26/06/2018.
 */

public class ReservePresenterImp implements ReservePresenter{

    private Context context;
    private ReserveView reserveView;
    private String token;

    public ReservePresenterImp(Context context, ReserveView reserveView, String token) {
        this.context = context;
        this.reserveView = reserveView;
        this.token = token;
    }

    private int dayFinal, monthFinal, yearFinal, hourFromReserve, minuteFromReserve, hourToReserve, minuteToReserve;

    public boolean validate(String name, String numberPhone, String quantity, String date, String time) {

        final Calendar c = Calendar.getInstance();

        boolean valid = true;
        if(name.isEmpty()){
            reserveView.alertMessage("Thông báo", "Vui lòng nhập tên hàng", 500);
            valid = false;
            return valid;
        }
        else if(numberPhone.isEmpty()){
            reserveView.alertMessage("Thông báo", "Vui lòng nhập số điện thoại", 500);
            valid = false;
            return valid;
        }
        else if(quantity.isEmpty()){
            reserveView.alertMessage("Thông báo", "Vui lòng nhập số lượng", 500);
            valid = false;
            return valid;
        }
        else if(date.isEmpty()){
            reserveView.alertMessage("Thông báo", "Vui lòng nhập ngày khách đến", 500);
            valid = false;
            return valid;
        }
        else if(time.isEmpty()){
            reserveView.alertMessage("Thông báo", "Vui lòng nhập ngày khách đến", 500);
            valid = false;
            return valid;
        }



        String[] arrayDate = date.split("\\/");

        if(!date.isEmpty()){
            if(Integer.parseInt(arrayDate[2]) < c.get(Calendar.YEAR)){
                reserveView.alertMessage("Thông báo", "Ngày đặt không được trước ngày hiện tại", 500);
                valid = false;
                return valid;
            }else if(Integer.parseInt(arrayDate[2]) == c.get(Calendar.YEAR)){
                if(Integer.parseInt(arrayDate[1]) < c.get(Calendar.MONTH)+1){
                    reserveView.alertMessage("Thông báo", "Ngày đặt không được trước ngày hiện tại", 500);
                    valid = false;
                    return valid;
                }else if(Integer.parseInt(arrayDate[1]) == c.get(Calendar.MONTH)+1){
                    if(Integer.parseInt(arrayDate[0]) < c.get(Calendar.DAY_OF_MONTH)){
                        reserveView.alertMessage("Thông báo", "Ngày đặt không được trước ngày hiện tại", 500);
                        valid = false;
                        return valid;
                    }else if(Integer.parseInt(arrayDate[0]) == c.get(Calendar.DAY_OF_MONTH)){
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        String[] arrayTime = time.split("\\:");
                        String[] arrayCurrentTime = sdf.format(new Date()).split("\\:");

                        if(Integer.parseInt(arrayTime[0]) < Integer.parseInt(arrayCurrentTime[0])){
                            reserveView.alertMessage("Thông báo", "Thời gian đặt không được sau thời gian hiện tại", 500);
                            valid = false;
                            return valid;
                        }else if(Integer.parseInt(arrayTime[0]) == Integer.parseInt(arrayCurrentTime[0])){
                            if(Integer.parseInt(arrayTime[1]) <= Integer.parseInt(arrayCurrentTime[1])){
                                reserveView.alertMessage("Thông báo", "Thời gian đặt không được sau thời gian hiện tại", 500);
                                valid = false;
                                return valid;
                            }
                        }
                    }
                }
            }
        }

        if(!date.isEmpty()){
            Date afterAWeek = new Date();
            c.setTime(afterAWeek);
            c.add(Calendar.WEEK_OF_MONTH, 1);
            afterAWeek = c.getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String[] arrayAfterAWeek = dateFormat.format(afterAWeek).split("\\/");
            if(Integer.parseInt(arrayAfterAWeek[2]) < Integer.parseInt(arrayDate[2])){
                reserveView.alertMessage("Thông báo", "Chỉ được đặt trước trong vòng 1 tuần", 500);
                valid = false;
                return valid;
            }else if(Integer.parseInt(arrayAfterAWeek[2]) == Integer.parseInt(arrayDate[2])){
                if(Integer.parseInt(arrayAfterAWeek[1]) < Integer.parseInt(arrayDate[1])){
                    reserveView.alertMessage("Thông báo", "Chỉ được đặt trước trong vòng 1 tuần", 500);
                    valid = false;
                    return valid;
                }else if(Integer.parseInt(arrayAfterAWeek[1]) == Integer.parseInt(arrayDate[1])){
                    if(Integer.parseInt(arrayAfterAWeek[0]) < Integer.parseInt(arrayDate[0])){
                        reserveView.alertMessage("Thông báo", "Chỉ được đặt trước trong vòng 1 tuần", 500);
                        valid = false;
                        return valid;
                    }
                }
            }
        }

        return valid;
    }
    @Override
    public void validateNewReserve(String name, String numberPhone, String quantity, String date, String time) {
        if(!validate(name, numberPhone, quantity, date, time)){
            return;
        }
        reserveView.alertMessage("Thông báo", "OK ne", 200);
    }

    @Override
    public void createReserve(String name, String numberPhone, String quantity, String date, String time, String note) {
        validateNewReserve(name, numberPhone, quantity, date, time);

    }
}
