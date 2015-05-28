package mil.nga.giat.asam.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import mil.nga.giat.asam.R;
import mil.nga.giat.asam.util.SyncTime;

/**
 * Created by wnewman on 5/28/15.
 */
public class SettingsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    public SettingsAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        SettingsViewHolder viewHolder;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.settings_row, null);
            viewHolder = new SettingsViewHolder(view);
            view.setTag(viewHolder);

            viewHolder.subtitle.setVisibility(position < 4 ? View.GONE : View.VISIBLE);
        } else {
            viewHolder = (SettingsViewHolder) view.getTag();
        }

        switch (position) {
            case 0:
                viewHolder.title.setText(context.getString(R.string.all_asams_about_title_text));
                break;
            case 1:
                viewHolder.title.setText(context.getString(R.string.disclaimer_title_text));
                break;
            case 2:
                viewHolder.title.setText(context.getString(R.string.legal_fragment_nga_privacy_policy_label_text));
                break;
            case 3:
                viewHolder.title.setText(context.getString(R.string.legal_fragment_nga_open_source_licenses_label_text));
                break;
            case 4:
                viewHolder.title.setText(String.format(context.getString(R.string.preferences_last_sync_time_label_text), SyncTime.getLastSyncTimeAsText(context)));
                viewHolder.subtitle.setText(context.getString(R.string.preferences_click_to_sync_label_text));
                break;
        }


        return view;
    }

    class SettingsViewHolder {
        public TextView title;
        public TextView subtitle;

        public SettingsViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
        }
    }
}
