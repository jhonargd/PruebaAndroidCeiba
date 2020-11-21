package co.com.ceiba.mobile.pruebadeingreso.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private ArrayList<Post> listpost;
    Context mContext;
    public PostAdapter(Context applicationContext, ArrayList<Post> listpost) {
        this.listpost = listpost;
        this.mContext = applicationContext;
    }
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        final PostAdapter.ViewHolder viewHolder = new PostAdapter.ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final PostAdapter.ViewHolder holder, final int position) {
        Post post = listpost.get(position);
        holder.title.setText(post.getTitle().toString());
        holder.body.setText(post.getBody().toString());
    }
    @Override
    public int getItemCount() {
        return listpost.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView body;
        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            body = v.findViewById(R.id.body);
        }
    }
}
