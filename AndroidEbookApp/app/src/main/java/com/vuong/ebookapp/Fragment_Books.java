package com.vuong.ebookapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Books extends Fragment {
    public static final String TAG = "category";

    GridView lsv_allnews;
    List<ItemBooks> arrayOfAllnews;
    Adapter_Books objAdapter;
    AlertDialogManager alert = new AlertDialogManager();
    private ItemBooks objAllBean;
    ArrayList<String> allListCatid,allListCatName,allListCatAuthorName, allListCatImageUrl;
    String[] allArrayCatid,allArrayCatname,allArrayCatAuthorname,allArrayCatImageurl;
    int textlength = 0;


    public Fragment_Books() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.book_list, container, false);



        setHasOptionsMenu(true);
        lsv_allnews=(GridView) v.findViewById(R.id.book_grid);
        arrayOfAllnews=new ArrayList<ItemBooks>();
        setHasOptionsMenu(true);

        allListCatid=new ArrayList<String>();
        allListCatImageUrl=new ArrayList<String>();
        allListCatName=new ArrayList<String>();
        allListCatAuthorName=new ArrayList<String>();

        allArrayCatid=new String[allListCatid.size()];
        allArrayCatname=new String[allListCatName.size()];
        allArrayCatAuthorname=new String[allListCatAuthorName.size()];
        allArrayCatImageurl=new String[allListCatImageUrl.size()];

        lsv_allnews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                objAllBean=arrayOfAllnews.get(position);
                int Catid=objAllBean.getCategoryId();
                Constant.CATEGORY_IDD=objAllBean.getCategoryId();
                Log.e("cat_id", "" + Catid);
                Constant.CATEGORY_TITLE=objAllBean.getCategoryName();

                Intent intcat=new Intent(getActivity(),Fragment_StoryList.class);
                startActivity(intcat);
            }
        });


        if (JsonUtils.isNetworkAvailable(getActivity())) {
            new MyTask().execute(Constant.CATEGORY_URL);
        } else {
            showToast("Không có kết nối mạng!!!");
            alert.showAlertDialog(getActivity(), "Lỗi kết nối",
                    "Vui lòng kết nối tới 1 mạng", false);
        }
        return v;
    }

    private	class MyTask extends AsyncTask<String, Void, String> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Đang tải...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (null != pDialog && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (null == result || result.length() == 0) {
                showToast("Không có dữ liệu từ trang chủ!!!");

            } else {

                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
                    JSONObject objJson = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);

                        ItemBooks objItem = new ItemBooks();
                        objItem.setCategoryName(objJson.getString(Constant.CATEGORY_NAME));
                        objItem.setCategoryAuthorName(objJson.getString(Constant.CATEGORY_AUTHOR));
                        objItem.setCategoryId(objJson.getInt(Constant.CATEGORY_CID));
                        objItem.setCategoryImageurl(objJson.getString(Constant.CATEGORY_IMAGE));
                        arrayOfAllnews.add(objItem);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                for(int j=0;j<arrayOfAllnews.size();j++)
                {
                    objAllBean=arrayOfAllnews.get(j);

                    allListCatid.add(String.valueOf(objAllBean.getCategoryId()));
                    allArrayCatid=allListCatid.toArray(allArrayCatid);

                    allListCatName.add(objAllBean.getCategoryName());
                    allArrayCatname=allListCatName.toArray(allArrayCatname);

                    allListCatAuthorName.add(objAllBean.getCategoryAuthorName());
                    allArrayCatAuthorname=allListCatAuthorName.toArray(allArrayCatAuthorname);

                    allListCatImageUrl.add(objAllBean.getCategoryImageurl());
                    allArrayCatImageurl=allListCatImageUrl.toArray(allArrayCatImageurl);

                }

                setAdapterToListview();
            }

        }
    }



    public void setAdapterToListview() {
        objAdapter = new Adapter_Books(getActivity(), R.layout.lsv_item_books,
                arrayOfAllnews);
        lsv_allnews.setAdapter(objAdapter);
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView)
                MenuItemCompat.getActionView(menu.findItem(R.id.search));

        final MenuItem searchMenuItem = menu.findItem(R.id.search);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (!hasFocus) {
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("", false);
                }
            }
        });

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                textlength = newText.length();
                arrayOfAllnews.clear();

                for (int i = 0; i < allArrayCatname.length; i++) {
                    if (textlength <= allArrayCatname[i].length()) {
                        if (newText.toString().equalsIgnoreCase((String) allArrayCatname[i].subSequence(0, textlength))) {
                            ItemBooks objItem = new ItemBooks();
                            objItem.setCategoryId(Integer.parseInt(allArrayCatid[i]));
                            objItem.setCategoryName(allArrayCatname[i]);
                            objItem.setCategoryAuthorName(allArrayCatAuthorname[i]);
                            objItem.setCategoryImageurl(allArrayCatImageurl[i]);

                            arrayOfAllnews.add(objItem);
                        }
                    }
                }

                setAdapterToListview();

                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:

                return true;

            case R.id.menu_moreapp:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.fb_author))));
                return true;

            case R.id.menu_rateapp:
                final String appName = getActivity().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com/Vương-Ebook-1040163346175263")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com/Vương-Ebook-1040163346175263")));
                }
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
