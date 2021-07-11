package es.ulpgc.eite.da.orderingitems.items;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import es.ulpgc.eite.da.orderingitems.R;
import es.ulpgc.eite.da.orderingitems.app.AppMediator;
import es.ulpgc.eite.da.orderingitems.data.ItemData;
import es.ulpgc.eite.da.orderingitems.item.ItemDetailActivity;



public class ItemListActivity
    extends AppCompatActivity implements ItemListContract.View {

  public static String TAG = ItemListActivity.class.getSimpleName();

  private ItemListContract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_list);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle(R.string.list_screen);

    if (savedInstanceState == null) {
      AppMediator.resetInstance();
    }

    // do the setup
    ItemListScreen.configure(this);

    if (savedInstanceState == null) {
      presenter.onStart();

    } else {
      presenter.onRestart();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    // load the data
    presenter.onResume();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();

    presenter.onBackPressed();
  }

  @Override
  protected void onPause() {
    super.onPause();

    presenter.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    presenter.onDestroy();
  }

  public void onButtonTapped(View view) {
    presenter.onButtonTapped();
  }

  @Override
  public void onDataUpdated(ItemListViewModel viewModel) {
    //Log.e(TAG, "onDataUpdated()");


    // deal with the datasource
    ((ListView) findViewById(R.id.itemList)).setAdapter(new ItemListAdapter(
            this, viewModel.dataSource, new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            ItemData data = (ItemData) view.getTag();
            presenter.onListTapped(data);
          }
        })
    );
  }


  @Override
  public void navigateToNextScreen() {
    Intent intent = new Intent(this, ItemDetailActivity.class);
    startActivity(intent);
  }

  @Override
  public void injectPresenter(ItemListContract.Presenter presenter) {
    this.presenter = presenter;
  }
}
