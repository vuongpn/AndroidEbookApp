package com.vuong.ebookapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

public class Fragment_DetailStory extends ActionBarActivity {

	int position;
	String[] allArrayNewsCId,allArrayNewsCatId,allArrayNewsCatImage,allArrayNewsCatName,allArrayNewsHeading,allArrayNewsImage,allArrayNewsDes,allArrayNewsDate;
	ViewPager viewpager;
	public ImageLoader imageLoader; 
	int TOTAL_IMAGE;
	public DatabaseHandler db;
	private Menu menu;
	String newscid,newscat_id,newscatname,newsheading,newsimage,newsdes,newsdate;
    private Toolbar toolbar;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_stories);


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

		setTitle(Constant.CATEGORY_TITLE);

		db = new DatabaseHandler(this);
		Intent i=getIntent();

		position=i.getIntExtra("POSITION", 0);
		allArrayNewsCId=i.getStringArrayExtra("CATEGORY_ITEM_CID");
		allArrayNewsCatName=i.getStringArrayExtra("CATEGORY_ITEM_NAME");
		allArrayNewsCatImage=i.getStringArrayExtra("CATEGORY_ITEM_IMAGE");
		allArrayNewsCatId=i.getStringArrayExtra("CATEGORY_ITEM_CAT_ID");
		allArrayNewsImage=i.getStringArrayExtra("CATEGORY_ITEM_NEWSIMAGE");
		allArrayNewsHeading=i.getStringArrayExtra("CATEGORY_ITEM_NEWSHEADING");
		allArrayNewsDes=i.getStringArrayExtra("CATEGORY_ITEM_NEWSDESCRI");
		allArrayNewsDate=i.getStringArrayExtra("CATEGORY_ITEM_NEWSDATE");


		//TOTAL_IMAGE=allArraynews.length-1;
		viewpager=(ViewPager)findViewById(R.id.news_slider);
		imageLoader=new ImageLoader(getApplicationContext());

		ImagePagerAdapter adapter = new ImagePagerAdapter();
		viewpager.setAdapter(adapter);

		boolean found = false;
		int j1=0;
		for(int i1=0;i1<allArrayNewsCatId.length;i1++)
		{
			if(allArrayNewsCatId[i1].contains(String.valueOf(position)))
			{
				found=true;
				j1=i1;
				break; 
			}
		}
		if(found)
		{
			viewpager.setCurrentItem(j1);
		}

		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub

				position=viewpager.getCurrentItem();
				newscat_id=allArrayNewsCatId[position];

				List<Pojo> pojolist=db.getFavRow(newscat_id);
				if(pojolist.size()==0)
				{
					menu.getItem(2).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_outline));
				}
				else
				{	
					if(pojolist.get(0).getCatId().equals(newscat_id))
					{
						menu.getItem(2).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_white));
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int position) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int position) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu, menu);
		this.menu = menu;
		FirstFav();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		switch (menuItem.getItemId()) 
		{
		case android.R.id.home: 
			onBackPressed();
			return true;

		case R.id.menu_back:
			position=viewpager.getCurrentItem();
			position--;
			if (position < 0) {
				position = 0;
			}
			viewpager.setCurrentItem(position);
			return true;

		case R.id.menu_next:

			position=viewpager.getCurrentItem();
			position++;
			if (position == TOTAL_IMAGE) {
				position = TOTAL_IMAGE;
			}
			viewpager.setCurrentItem(position);
			return true;

		case R.id.menu_fav:

			position=viewpager.getCurrentItem();
			newscat_id=allArrayNewsCatId[position];

			List<Pojo> pojolist=db.getFavRow(newscat_id);
			if(pojolist.size()==0)
			{
				AddtoFav(position);//if size is zero i.e means that record not in database show add to favorite 
			}
			else
			{	
				if(pojolist.get(0).getCatId().equals(newscat_id))
				{
					RemoveFav(position);
				}
			}
			return true;

		case R.id.menu_share:

			position=viewpager.getCurrentItem();
			newsheading=allArrayNewsHeading[position];
			newsdes=allArrayNewsDes[position];
			String formattedString=android.text.Html.fromHtml(newsdes).toString();
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, newsheading+"\n"+formattedString+"\n"+" App này thật tuyệt , cho app 5 sao nào "+"https://www.facebook.com/Vương-Ebook-1040163346175263/");
			sendIntent.setType("text/plain");
			startActivity(sendIntent); 

			return true;

		default:
			return super.onOptionsItemSelected(menuItem);
		}

	}
	public  String RemoveTag(String html){

		html=html.replaceAll("<br/>","");

		return html;
	}
	public void AddtoFav(int position)
	{
		newscat_id=allArrayNewsCatId[position];
		newscid=allArrayNewsCId[position];
		newscatname=allArrayNewsCatName[position];
		//newscatimage=allArrayNewsCatImage[position];
		newsheading=allArrayNewsHeading[position];
		newsimage=allArrayNewsImage[position];
		newsdes=allArrayNewsDes[position];
		newsdate=allArrayNewsDate[position];

		db.AddtoFavorite(new Pojo(newscat_id,newscid,newscatname,newsheading,newsimage,newsdes,newsdate));
		Toast.makeText(getApplicationContext(), "Đã thêm vào Bookmark", Toast.LENGTH_SHORT).show();
		menu.getItem(2).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_white));


	}

	//remove from favorite
	public void RemoveFav(int position)
	{
		newscat_id=allArrayNewsCatId[position];
		db.RemoveFav(new Pojo(newscat_id));
		Toast.makeText(getApplicationContext(), "Đã xóa khỏi Bookmark", Toast.LENGTH_SHORT).show();
		menu.getItem(2).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_outline));

	}

	public void FirstFav()
	{
		int first=viewpager.getCurrentItem();
		String Image_id=allArrayNewsCatId[first];

		List<Pojo> pojolist=db.getFavRow(Image_id);
		if(pojolist.size()==0)
		{
			menu.getItem(2).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_outline));

		}
		else
		{	
			if(pojolist.get(0).getCatId().equals(Image_id))
			{
				menu.getItem(2).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_white));

			}

		}
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private LayoutInflater inflater;

		public ImagePagerAdapter() {
			// TODO Auto-generated constructor stub

			inflater = getLayoutInflater();
		}

		@Override
		public int getCount() {
			return allArrayNewsCatId.length;

		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {

			View imageLayout = inflater.inflate(R.layout.newpager_story_details, container, false);
			assert imageLayout != null;

			ImageView news_imageview=(ImageView)imageLayout.findViewById(R.id.image_news);
			TextView txt_newstitle=(TextView)imageLayout.findViewById(R.id.text_newstitle);
			TextView txt_newsdate=(TextView)imageLayout.findViewById(R.id.text_newsdate);

			WebView webnewsdes=(WebView)imageLayout.findViewById(R.id.webView_newsdes);

			imageLoader.DisplayImage(Constant.SERVER_IMAGE_NEWSLISTDETAILS+allArrayNewsImage[position], news_imageview);

			txt_newstitle.setText(allArrayNewsHeading[position]);
			txt_newsdate.setText(allArrayNewsDate[position]);
			webnewsdes.setBackgroundColor(Color.parseColor(getString(R.color.background_color)));
			webnewsdes.setFocusableInTouchMode(false);
			webnewsdes.setFocusable(false);
			webnewsdes.getSettings().setDefaultTextEncodingName("UTF-8");

			String mimeType = "text/html; charset=UTF-8";
			String encoding = "utf-8";
			String htmlText = allArrayNewsDes[position];

			String text = "<html><head>"
					+ "<style type=\"text/css\">body{color: #525252;}"
					+ "</style></head>"
					+ "<body>"                          
					+  htmlText
					+ "</body></html>";

			webnewsdes.loadData(text, mimeType, encoding);

			container.addView(imageLayout, 0);
			return imageLayout;

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}
	}

	@Override
	protected void onPause() {

		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

}
