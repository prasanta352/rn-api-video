import * as React from 'react';

import { Button, StyleSheet, View } from 'react-native';
import { RnApiVideoView } from 'rn-api-video';

export default function App() {
  const [isPlaying, setIsPlaying] = React.useState(false);
  const [isMuted, setIsMuted] = React.useState(false);
  return (
    <View style={styles.container}>
      <RnApiVideoView
        videoId="viNvgh1Dl7csfXr1QJcLTLf"
        color="#32a852"
        style={styles.box}
        autoplay={false}
        isPlaying={isPlaying}
        isMuted={isMuted}
      />

      <Button
        title={isPlaying ? 'Pause' : 'Play'}
        onPress={() => {
          console.log('test');
          setIsPlaying((p) => !p);
        }}
      />
      <Button
        title={isMuted ? 'Unmute' : 'Mute'}
        onPress={() => {
          console.log('test');
          setIsMuted((p) => !p);
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: '100%',
    height: 600,
    marginVertical: 20,
  },
});
