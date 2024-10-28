package com.recipeapp.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.recipeapp.datahandler.DataHandler;
import com.recipeapp.model.Recipe;

public class RecipeUI {
    private BufferedReader reader;
    private DataHandler dataHandler;

    public RecipeUI(DataHandler dataHandler) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.dataHandler = dataHandler;
    }

    public void displayMenu() {

        System.out.println("Current mode: " + dataHandler.getMode());

        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        displayRecipes();
                        break;
                    case "2":
                        addNewRecipe();
                        break;
                    case "3":
                        searchRecipe();
                        break;
                    case "4":
                        System.out.println("Exiting the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    private void displayRecipes() {
        try {
            List<Recipe> recipes = dataHandler.readData();
            if (recipes.isEmpty()) {
                System.out.println("No recipes available.");
            }

            System.out.println("Recipes:");
            System.out.println("-----------------------------------");
            for (Recipe recipe : recipes) {
                System.out.println("Recipe Name: " + recipe.getName());
                System.out.print("Main Ingredients: ");
                String str = String.join(",", recipe.getIngredients());
                System.out.println(str);
                System.out.println("-----------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewRecipe() {
        String name = "";
        String line;
        ArrayList<String> ingredients = new ArrayList<>();
        System.out.println("Adding a new recipe.");
        try {
            System.out.print("Enter recipe name: ");
            name = reader.readLine();
            System.out.println("Enter ingredients (type 'done' when finished):");
            while (!(line = reader.readLine()).equals("done")) {
                System.out.print("Ingredient: ");
                ingredients.add(line);
            }
            dataHandler.writeData(new Recipe(name, ingredients));
        } catch (IOException e) {
            System.out.println("Failed to add new recipe: " + e.getMessage());
        }
        System.out.println("Recipe added successfully.");
    }

    private void searchRecipe() {
        try {
            System.out.print("Enter search query (e.g., 'name=Tomato&ingredient=Garlic'): ");
            String line = reader.readLine();
            ArrayList<Recipe> searchedList = dataHandler.searchData(line);
            if (searchedList.isEmpty()) {
                System.out.println("No matching recipes found.");
            }

            for (Recipe recipe : searchedList) {
                System.out.println("Recipe Name: " + recipe.getName());
                System.out.print("Main Ingredients: ");
                System.out.println(String.join(",", recipe.getIngredients()));
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Failed to search recipes: " + e.getMessage());
        }
    }
}
