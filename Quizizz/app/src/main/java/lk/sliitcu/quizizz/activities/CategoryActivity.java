package lk.sliitcu.quizizz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lk.sliitcu.quizizz.R;
import lk.sliitcu.quizizz.adapters.CategoryAdapter;
import lk.sliitcu.quizizz.models.Category;
import lk.sliitcu.quizizz.utils.VolleySingleton;

public class CategoryActivity extends AppCompatActivity {

    private final List<Category> categoryList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerViewCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryAdapter = new CategoryAdapter(categoryList, category -> {
            Intent intent = new Intent(CategoryActivity.this, GameActivity.class);
            intent.putExtra("CATEGORY_ID", category.getId());
            intent.putExtra("CATEGORY_NAME", category.getName());
            startActivity(intent);
        });

        recyclerView.setAdapter(categoryAdapter);
        fetchCategories();
    }

    private void fetchCategories() {
        String url = "https://opentdb.com/api_category.php";
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        JSONArray categories = response.getJSONArray("trivia_categories");
                        categoryList.clear();

                        int count = Math.min(categories.length(), 10);
                        for (int i = 0; i < count; i++) {
                            JSONObject cat = categories.getJSONObject(i);
                            categoryList.add(new Category(cat.getInt("id"), cat.getString("name")));
                        }

                        Collections.sort(categoryList, Comparator.comparing(Category::getName));

                        categoryAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing data!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }, error -> {
                    Toast.makeText(this, "Failed to fetch categories!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}