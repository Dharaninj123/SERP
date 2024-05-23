package com.example.schoolerp;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String itemList[];
    int itemImages[];
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, String[] itemList, int[] itemImages) {
        this.context = ctx;
        this.itemList = itemList;
        this.itemImages = itemImages;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return itemList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView txtView = convertView.findViewById(R.id.textView);
        ImageView listImg = convertView.findViewById(R.id.ImageIcon);
        txtView.setText(itemList[i]);
        listImg.setImageResource(itemImages[i]);
        return convertView;
    }
}
