package co.com.ceiba.mobile.pruebadeingreso.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.model.UserInterface;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<User> listUser;
    Context mContext;
    private UserInterface serviceTotalcallback;
    public UserAdapter(Context applicationContext, ArrayList<User> listUser, UserInterface serviceTotalcallback) {
        this.listUser = listUser;
        this.mContext = applicationContext;
        this.serviceTotalcallback = serviceTotalcallback;
    }
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        final UserAdapter.ViewHolder viewHolder = new UserAdapter.ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final UserAdapter.ViewHolder holder, final int position) {
        final User user = listUser.get(position);
        holder.name.setText(user.getName().toString());
        holder.phone.setText(user.getPhone());
        holder.email.setText(user.getEmail());
        holder.btn_view_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceTotalcallback.loadUser(user);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listUser.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phone;
        public TextView email;
        public Button btn_view_post;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            phone = v.findViewById(R.id.phone);
            email = v.findViewById(R.id.email);
            btn_view_post = v.findViewById(R.id.btn_view_post);
        }
    }
}
