package cn.goal.goal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import cn.goal.goal.services.UserService;
import cn.goal.goal.services.object.Goal;
import org.json.JSONException;

/**
 * Created by chenlin on 13/02/2017.
 */
public class GoalDetailActivity extends AppCompatActivity implements View.OnClickListener, RadioButton.OnCheckedChangeListener {
    private TextView title;
    private TextView content;
    private RadioButton radioButtonFinished;
    private RadioButton radioButtonUnfinished;
    private TextView begin;
    private TextView plan;
    private TextView end;
    private TextView createAt;
    private ImageButton buttonBack;
    private ImageButton buttonMenu;
    private PopupMenu mPopupMenu;

    private int goalIndex;
    private Goal goal; // 存放goal信息

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        // 传入数据不正确
        if (getIntent() == null) {
            Toast.makeText(this, "传入数据错误", Toast.LENGTH_SHORT).show();
            finish();
            return ;
        }
        goalIndex = getIntent().getExtras().getInt("goalIndex");
        try {
            goal = (Goal) UserService.getGoals().get(goalIndex);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "传入数据错误", Toast.LENGTH_SHORT).show();
            finish();
            return ;
        }

        radioButtonFinished = (RadioButton) findViewById(R.id.radioButtonFinished);
        radioButtonFinished.setOnCheckedChangeListener(this);
        radioButtonUnfinished = (RadioButton) findViewById(R.id.radioButtonUnfinished);
        radioButtonUnfinished.setOnCheckedChangeListener(this);

        title = (TextView) findViewById(R.id.goal_title);
        content = (TextView) findViewById(R.id.content);end = (TextView) findViewById(R.id.end);
        begin = (TextView) findViewById(R.id.begin);
        plan = (TextView) findViewById(R.id.plan);
        createAt = (TextView) findViewById(R.id.createAt);

        buttonBack = (ImageButton) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(this);
        buttonMenu = (ImageButton) findViewById(R.id.button_menu);
        buttonMenu.setOnClickListener(this);

        render();
        createMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 刷新goal数据;
        render();
    }

    private void render() {
        title.setText(goal.getTitle());
        content.setText(goal.getContent());
        radioButtonFinished.setChecked(goal.getFinished() == 1);
        radioButtonUnfinished.setChecked(goal.getFinished() == 0);
        end.setText(goal.getEnd());
        begin.setText(goal.getBegin());
        plan.setText(goal.getPlan());
        createAt.setText(String.format(getResources().getString(R.string.create_at), goal.getCreateAt()));
    }

    private void createMenu() {
        mPopupMenu = new PopupMenu(this, buttonMenu);
        mPopupMenu.getMenuInflater()
                .inflate(R.menu.goal_operation_popupmenu, mPopupMenu.getMenu());

        // 如果目标未完成则设置"标记已完成"菜单为可点击
        mPopupMenu.getMenu().getItem(3).setEnabled(goal.getFinished() == 0);
        // 如果目标已完成则设置"标记未完成"菜单为可点击
        mPopupMenu.getMenu().getItem(4).setEnabled(goal.getFinished() == 1);

        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String title = item.getTitle().toString();
                final String menuEdit = getResources().getString(R.string.menu_edit);
                final String menuDelete = getResources().getString(R.string.menu_delete);
                final String menuFinishedToday = getResources().getString(R.string.menu_mark_finished_today);
                final String menuFinished = getResources().getString(R.string.menu_mark_finished);
                final String menuUnfinished = getResources().getString(R.string.menu_mark_unfinished);

                if (title.equals(menuEdit)) {
                    handleEdit();
                } else if (title.equals(menuDelete)) {
                    handleDelete();
                } else if (title.equals(menuFinishedToday)) {
                    handleMarkFinishedToday();
                } else if (title.equals(menuUnfinished)) {
                    handleMarkUnfinished();
                } else if (title.equals(menuFinished)) {
                    handleMarkFinished();
                }
                return true;
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.radioButtonFinished:
                handleMarkFinished();
                break;
            case R.id.radioButtonUnfinished:
                handleMarkUnfinished();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_menu:
                mPopupMenu.show();
                break;
        }
    }

    private void handleEdit() {
        Intent intent = new Intent(this, GoalEditActivity.class);
        intent.putExtra("goalIndex", goalIndex);
        startActivity(intent);
    }

    private void handleDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_goal_dialog_info);
        builder.setTitle(R.string.delete_goal_dialog_title);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                UserService.deleteGoal(goal);
                finish();
            }
        });

        builder.create().show();
    }

    private void handleMarkFinished() {

    }

    private void handleMarkUnfinished() {

    }

    private void handleMarkFinishedToday() {

    }
}
