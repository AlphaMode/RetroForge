//package net.minecraftforge.oredict;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.List;
//
//import net.fakecraft.world.level.block.Block;
//import net.fakecraft.world.item.crafting.Recipe;
//import net.fakecraft.world.inventory.CraftingContainer;
//import net.fakecraft.world.item.Item;
//import net.fakecraft.world.item.ItemInstance;
//import net.fakecraft.world.item.crafting.ShapelessRecipes;
//import net.fakecraft.world.level.Level;
//
//public class ShapelessOreRecipe implements Recipe
//{
//    private ItemInstance output = null;
//    private ArrayList<Object> input = new ArrayList<Object>();
//
//    public ShapelessOreRecipe(Block result, Object... recipe){ this(new ItemInstance(result), recipe); }
//    public ShapelessOreRecipe(Item  result, Object... recipe){ this(new ItemInstance(result), recipe); }
//
//    public ShapelessOreRecipe(ItemInstance result, Object... recipe)
//    {
//        output = result.copy();
//        for (Object in : recipe)
//        {
//            if (in instanceof ItemInstance)
//            {
//                input.add(((ItemInstance)in).copy());
//            }
//            else if (in instanceof Item)
//            {
//                input.add(new ItemInstance((Item)in));
//            }
//            else if (in instanceof Block)
//            {
//                input.add(new ItemInstance((Block)in));
//            }
//            else if (in instanceof String)
//            {
//                input.add(OreDictionary.getOres((String)in));
//            }
//            else
//            {
//                String ret = "Invalid shapeless ore recipe: ";
//                for (Object tmp :  recipe)
//                {
//                    ret += tmp + ", ";
//                }
//                ret += output;
//                throw new RuntimeException(ret);
//            }
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    ShapelessOreRecipe(ShapelessRecipes recipe, Map<ItemInstance, String> replacements)
//    {
//        output = recipe.getRecipeOutput();
//
//        for(ItemInstance ingred : ((List<ItemInstance>)recipe.recipeItems))
//        {
//            Object finalObj = ingred;
//            for(Entry<ItemInstance, String> replace : replacements.entrySet())
//            {
//                if(OreDictionary.itemMatches(replace.getKey(), ingred, false))
//                {
//                    finalObj = OreDictionary.getOres(replace.getValue());
//                    break;
//                }
//            }
//            input.add(finalObj);
//        }
//    }
//
//    @Override
//    public int size(){ return input.size(); }
//
//    @Override
//    public ItemInstance getResultItem(){ return output; }
//
//    @Override
//    public ItemInstance assemble(CraftingContainer var1){ return output.copy(); }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public boolean matches(CraftingContainer var1, Level world)
//    {
//        ArrayList<Object> required = new ArrayList<Object>(input);
//
//        for (int x = 0; x < var1.getContainerSize(); x++)
//        {
//            ItemInstance slot = var1.getItem(x);
//
//            if (slot != null)
//            {
//                boolean inRecipe = false;
//                Iterator<Object> req = required.iterator();
//
//                while (req.hasNext())
//                {
//                    boolean match = false;
//
//                    Object next = req.next();
//
//                    if (next instanceof ItemInstance)
//                    {
//                        match = OreDictionary.itemMatches((ItemInstance)next, slot, false);
//                    }
//                    else if (next instanceof ArrayList)
//                    {
//                        Iterator<ItemInstance> itr = ((ArrayList<ItemInstance>)next).iterator();
//                        while (itr.hasNext() && !match)
//                        {
//                            match = OreDictionary.itemMatches(itr.next(), slot, false);
//                        }
//                    }
//
//                    if (match)
//                    {
//                        inRecipe = true;
//                        required.remove(next);
//                        break;
//                    }
//                }
//
//                if (!inRecipe)
//                {
//                    return false;
//                }
//            }
//        }
//
//        return required.isEmpty();
//    }
//
//    /**
//     * Returns the input for this recipe, any mod accessing this value should never
//     * manipulate the values in this array as it will effect the recipe itself.
//     * @return The recipes input vales.
//     */
//    public ArrayList<Object> getInput()
//    {
//        return this.input;
//    }
//}
