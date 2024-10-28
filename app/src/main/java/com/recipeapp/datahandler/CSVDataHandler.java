package com.recipeapp.datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.recipeapp.model.Recipe;

public class CSVDataHandler implements DataHandler {
    private String filePath;

    public CSVDataHandler() {
        this.filePath = "app/src/main/resources/recipes.csv";
    }

    public CSVDataHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getMode() {
        return "CSV";
    }

    @Override
    public ArrayList<Recipe> readData() throws IOException {
        // recipesリストを作成
        ArrayList<Recipe> recipes = new ArrayList<>();
        // 読み込み
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Recipe型なのでここで引数用に分離
                String[] values = line.split(",");
                ArrayList<String> ingredients = new ArrayList<>();
                for (int i = 1; i < values.length; i++) {
                    ingredients.add(values[i]);
                }
                recipes.add(new Recipe(values[0], ingredients));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    @Override
    public void writeData(Recipe recipe) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.newLine();
            writer.write(recipe.getName() + ",");
            writer.write(String.join(",", recipe.getIngredients()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Recipe> searchData(String keyWord) throws IOException {
        String[] pairs = keyWord.split("&");
        ArrayList<Recipe> recipes = readData();
        ArrayList<Recipe> searchRecipes = new ArrayList<>();
        boolean match = false;

        for (Recipe recipe : recipes) {
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (pairs.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    if (key.contains("name") && recipe.getName().contains(value)) {
                        match = true;
                    }
                    if (key.contains("ingredient") && recipe.getIngredients().contains(value)) {
                        match = true;
                    }
                }
                if (match) searchRecipes.add(recipe);
            }
        }
        return searchRecipes;
    }

}
