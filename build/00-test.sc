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
f = [1,1,1,0,1,1,0,1,0,1,1,0];
g = [0,1,0,1,1,0,1,1,1,0,1,1];

Routine{
	inf.do{|i|
		if (f[i%f.size] ==1,{
				n.play(y);
		});
		if (i>(12*4),{
			if (g[i%g.size] ==1,{
				n.play(h);
			});
		});



		(0.3 * 0.5).wait;
	}

}.play;

)