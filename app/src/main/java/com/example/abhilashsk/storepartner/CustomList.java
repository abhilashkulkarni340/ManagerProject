package com.example.abhilashsk.storepartner;

/**
 * Created by abhilashsk on 02/03/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final ArrayList<String> orderId;
    private final ArrayList<String> orderPhone;
    private final ArrayList<String> orderPrice;
    View rowView;
    TextView orderphone,orderid,orderprice;
    public CustomList(Activity context,
                      ArrayList<String> orderId, ArrayList<String> orderPhone, ArrayList<String> orderPrice) {
        super(context, R.layout.list_single, orderId);
        this.context = context;
        this.orderId = orderId;
        this.orderPhone = orderPhone;
        this.orderPrice=orderPrice;

    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        rowView= inflater.inflate(R.layout.list_single, null, true);
        orderid = (TextView) rowView.findViewById(R.id.order_id);
        orderphone = (TextView) rowView.findViewById(R.id.order_phone_num);
        orderprice=(TextView)rowView.findViewById(R.id.order_total_price);
        orderid.setText("Order ID: "+orderId.get(position));
        orderphone.setText(orderPhone.get(position));
        orderprice.setText("INR "+orderPrice.get(position));
        /*gps = new Tracer(view.getContext());
        lat1=gps.getLatitude();
        lon1=gps.getLongitude();

        GeocodingLocation locationAddress = new GeocodingLocation();
        Bundle bundle = locationAddress.getAddressFromLocation(locations.get(i),
                view.getApplicationContext());*/
        /*ImageView nav = (ImageView) rowView.findViewById(R.id.navigation_image);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),MapsActivity.class);
                intent.putExtra("destination_address_key",location.get(position));
                intent.putExtra("destination_name_key",shopName.get(position));
                view.getContext().startActivity(intent);
            }
        });*/

        RelativeLayout shop = (RelativeLayout) rowView.findViewById(R.id.rel_shop_list);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),OrderActivity.class);
                intent.putExtra("OrderId",orderId.get(position));
                intent.putExtra("userId",orderPhone.get(position));
                view.getContext().startActivity(intent);
            }
        });

        return rowView;
    }
}
