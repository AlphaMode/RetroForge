package cpw.mods.fml.client;

import com.google.common.collect.BiMap;
import com.google.common.collect.Maps;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.toposort.ModSortingException;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.fakecraft.network.INetHandler;
import net.fakecraft.network.NetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FMLClientHandler implements IFMLSidedHandler {
    /**
     * The singleton
     */
    private static final FMLClientHandler INSTANCE = new FMLClientHandler();

    /**
     * A reference to the server itself
     */
    private Minecraft client;

    private DummyModContainer optifineContainer;

    @SuppressWarnings("unused")
    private boolean guiLoaded;

    @SuppressWarnings("unused")
    private boolean serverIsRunning;

    private MissingModsException modsMissing;

    private ModSortingException modSorting;

    private boolean loading = true;

    private WrongMinecraftVersionException wrongMC;

//    private CustomModLoadingErrorDisplayException customError;

    private DuplicateModsFoundException dupesFound;

    private boolean serverShouldBeKilledQuietly;

    private List<PackResources> resourcePackList;

    @SuppressWarnings("unused")
    private ResourceManager resourceManager;

    private Map<String, PackResources> resourcePackMap;

    private BiMap<ModContainer, IModGuiFactory> guiFactories;

//    private Map<ServerStatusResponse,JsonObject> extraServerListData;
//    private Map<ServerData, ExtendedServerListData> serverDataTag;
//
//    private WeakReference<NetHandlerPlayClient> currentPlayClient;

    /**
     * Get the server instance
     */
    public Minecraft getClient()
    {
        return null;
    }

    /**
     * @return the instance
     */
    public static FMLClientHandler instance()
    {
        return INSTANCE;
    }

    /**
     * Called to start the whole game off
     *
     * @param minecraft The minecraft instance being launched
     * @param resourcePackList The resource pack list we will populate with mods
     * @param resourceManager The resource manager
     */
    @SuppressWarnings("unchecked")
    public void beginMinecraftLoading(Minecraft minecraft, @SuppressWarnings("rawtypes") List resourcePackList, ResourceManager resourceManager)
    {
//        detectOptifine();
//        SplashProgress.start();
        client = minecraft;
        this.resourcePackList = resourcePackList;
        this.resourceManager = resourceManager;
        this.resourcePackMap = Maps.newHashMap();
        if (minecraft.isDemo())
        {
            FMLLog.severe("DEMO MODE DETECTED, FML will not work. Finishing now.");
            haltGame("FML will not run in demo mode", new RuntimeException());
            return;
        }

        FMLCommonHandler.instance().beginLoading(this);
        try
        {
            Loader.instance().loadMods();
        }
        catch (WrongMinecraftVersionException wrong)
        {
            wrongMC = wrong;
        }
        catch (DuplicateModsFoundException dupes)
        {
            dupesFound = dupes;
        }
        catch (MissingModsException missing)
        {
            modsMissing = missing;
        }
        catch (ModSortingException sorting)
        {
            modSorting = sorting;
        }
//        catch (CustomModLoadingErrorDisplayException custom)
//        {
//            FMLLog.log(Level.ERROR, custom, "A custom exception was thrown by a mod, the game will now halt");
//            customError = custom;
//        }
        catch (LoaderException le)
        {
            haltGame("There was a severe problem during mod loading that has caused the game to fail", le);
            return;
        }
        finally
        {
//            client.refreshResources();
        }

        try
        {
            Loader.instance().preinitializeMods();
        }
//        catch (CustomModLoadingErrorDisplayException custom)
//        {
//            FMLLog.log(Level.ERROR, custom, "A custom exception was thrown by a mod, the game will now halt");
//            customError = custom;
//        }
        catch (LoaderException le)
        {
            haltGame("There was a severe problem during mod loading that has caused the game to fail", le);
            return;
        }
//        Map<String,Map<String,String>> sharedModList = (Map<String, Map<String, String>>) Launch.blackboard.get("modList");
//        if (sharedModList == null)
//        {
//            sharedModList = Maps.newHashMap();
//            Launch.blackboard.put("modList", sharedModList);
//        }
        for (ModContainer mc : Loader.instance().getActiveModList())
        {
            Map<String,String> sharedModDescriptor = mc.getSharedModDescriptor();
            if (sharedModDescriptor != null)
            {
                String sharedModId = "fml:"+mc.getModId();
//                sharedModList.put(sharedModId, sharedModDescriptor);
            }
        }
    }

    @Override
    public List<String> getAdditionalBrandingInformation() {
        return List.of();
    }

    @Override
    public Side getSide() {
        return null;
    }

    @Override
    public void haltGame(String message, Throwable exception) {

    }

    @Override
    public void showGuiScreen(Object clientGuiElement) {

    }

    @Override
    public void queryUser(StartupQuery query) throws InterruptedException {

    }

    @Override
    public void beginServerLoading(MinecraftServer server) {

    }

    @Override
    public void finishServerLoading() {

    }

    @Override
    public File getSavesDirectory() {
        return null;
    }

    @Override
    public MinecraftServer getServer() {
        return null;
    }

    @Override
    public boolean shouldServerShouldBeKilledQuietly() {
        return false;
    }

    @Override
    public void addModAsResource(ModContainer container) {

    }

    @Override
    public String getCurrentLanguage() {
        return "";
    }

    @Override
    public void serverStopped() {

    }

    @Override
    public NetworkManager getClientToServerNetworkManager() {
        return null;
    }

    @Override
    public INetHandler getClientPlayHandler() {
        return null;
    }

    @Override
    public void waitForPlayClient() {

    }

    @Override
    public void fireNetRegistrationEvent(EventBus bus, NetworkManager manager, Set<String> channelSet, String channel, Side side) {

    }

    @Override
    public boolean shouldAllowPlayerLogins() {
        return false;
    }

    @Override
    public void allowLogins() {

    }

    @Override
    public void processWindowMessages() {

    }

    @Override
    public String stripSpecialChars(String message) {
        return "";
    }
}
