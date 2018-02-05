package hello.kay.chestnut;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 功能概要描述：
 * 功能详细描述：
 * 作者： Kay
 * 创建日期： 18/2/5
 * 修改人：
 * 修改日期：
 * 版本号：
 * 版权所有：Copyright © 2015-2016 上海览益信息科技有限公司 http://www.lanyife.com
 */

public class ChildFragment extends Fragment {

    private String name;
    private RecyclerView viewRecycler;
    private ChestnutAdapter chestnutAdapter;

    public static ChildFragment obtain(String name) {
        ChildFragment childFragment = new ChildFragment();
        Bundle arguments = new Bundle();
        arguments.putString("title", name);
        childFragment.setArguments(arguments);
        return childFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);
        name = getArguments().getString("title");
        chestnutAdapter = new ChestnutAdapter();
        viewRecycler = view.findViewById(R.id.view_recycler);
        viewRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        viewRecycler.setAdapter(chestnutAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.item_divider));
        viewRecycler.addItemDecoration(decoration);
        return view;
    }

    private class ChestnutAdapter extends RecyclerView.Adapter<Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chestnut, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.textContent.setText(String.format("Hello %s, item %s", name, position));
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

    private static class Holder extends RecyclerView.ViewHolder {

        protected TextView textContent;

        public Holder(View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.text_content);
        }
    }
}
