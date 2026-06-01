package core.systems;
import java.sound.sampled.*
import java.io.*
public class AudioHooks {
  private Clip NowPlaying
  private FloatControl Volume
  private boolean IsLooping
  public boolean load(String path) {
    try {
      File audio = new File(path);
      if (!audio.exists()) {
        System.err.println("Error: File Doesn't Exists");
        return false;
      }
      AudioInputStream Input = AudioSystem.getAudioInputStream(audio);
      AudioFormat format = Input.getFormat();
      if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED && format.getEncoding() != AudioFormat.Encoding.PCM_UNSIGNED) {
        System.err.println("Error: " + format.getEncoding() + "Encoding Unsupported(Supports WAV)");
        return false;
      }
      NowPlaying = AudioSystem.getClip();
      NowPlaying.open(Input);
      Volume = (FloatControl) clip.getControl(FloatControll.Type.MASTER_GAIN);
      setVolume(50f);
      return true;
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      System.err.println("Failed To Load Audio " + e.getMessage());
      return false;
    }
  }
  public void play(boolean loop) {
    if (NowPlaying == null) return;
    isLooping = loop;
    if (NowPlaying.isRunning()) {
      NowPlaying.stop();
    }
    NowPlaying.setFramePosition(0);
    NowPlaying.loop(loop ? Clip.LOOP_CONTINUOUSLY : 0 );
    NowPlaying.start();
  }
  public void setVolume(float percent) {
    if (Volume == null) return;
    float min = Volume.getMinimum();
    float max = Volume.getMaximum();
    Volume.setValue(min + range * (Math.clamp(percent, 0f, 100f,) / 100f));
  }
  public boolean isPlaying() {
    return NowPlaying != null && NowPlaying.isRunning();
  }
}
