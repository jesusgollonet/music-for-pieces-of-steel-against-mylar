s.boot;
b = JGBUtil.dirToBuffers("/Users/jesusgollonet/Documents/projects/music-for-pieces-of-steel-against-mylar/samples/",s);
n= JGBSoundPlayer.new;

k = b.at(\clamp);
y = b.at(\k1p);
h = b.at(\k1r);
t = b.at(\clap);


n.play(k);
n.play(y);
n.play(h);

(
f = [0.5, 0.5, 1,0.5, 1.0,1.0,0.5,1.0] * 0.35;

Routine{
	inf.do{|i|
		i.postln;
		if(i%2==0,{n.play(y)}
			, {n.play(h)});
		(f[i%f.size]).wait;
	}

}.play;
)