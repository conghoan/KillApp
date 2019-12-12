package hoandc.killapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2019. All rights reserved
 *  Author HoanDC. Create on 12/12/2019.
 * ******************************************************************************
 */
public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {

    private Context mContext;
    private List<AppInfo> appInfos;
    private IKillApp mKillApp;

    AppAdapter(Context context, List<AppInfo> infos, IKillApp killApp) {
        this.mContext = context;
        this.appInfos = infos;
        this.mKillApp = killApp;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_app, parent, false);
        return new AppViewHolder(view, mKillApp);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        holder.updateInfo(appInfos.get(position));
    }

    @Override
    public int getItemCount() {
        if (appInfos == null) {
            return 0;
        }
        return appInfos.size();
    }

    void updateData(List<AppInfo> appInfos){
        this.appInfos = appInfos;
        notifyDataSetChanged();
    }

    static final class AppViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAppName;
        private Button btnKillApp;
        private IKillApp mIKillApp;

        AppViewHolder(@NonNull View itemView, IKillApp killApp) {
            super(itemView);
            mIKillApp = killApp;
            tvAppName = itemView.findViewById(R.id.tv_app_name);
            btnKillApp = itemView.findViewById(R.id.btn_kill);
        }

        void updateInfo(final AppInfo appInfo) {
            tvAppName.setText(appInfo.getAppName());
            btnKillApp.setTag(appInfo);
            btnKillApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppInfo appInfoTag = (AppInfo) v.getTag();
                    if (appInfoTag != null && mIKillApp != null) {
                        mIKillApp.OnKill(appInfoTag);
                    }
                }
            });
        }
    }

    public interface IKillApp {
        void OnKill(AppInfo appInfo);
    }
}
