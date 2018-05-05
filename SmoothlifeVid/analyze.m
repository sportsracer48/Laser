numfiles = 310;
imsize = 256;
nBins = 128;

if(0)
    images = zeros(imsize,imsize,numfiles);
    fimages = zeros(imsize,imsize,numfiles);
    radAves = zeros(nBins,numfiles);

    for i = 0:numfiles-1
        name = sprintf('frame_%06d.png',i);
        im = imread(name);
        im = im(:,:,1);
        fim = abs(fft2(im(:,:,1)));
        fim(1,1) = 0;
        fim = fftshift(fim);

        [radAve,bins] = radialavg(fim,128);

        radAve(isnan(radAve)) = 0;

        radAves(:,i+1) = radAve;
        images(:,:,i+1) = im;
        fimages(:,:,i+1) = fim;
    end
end

is = 0:nBins-1;
freqs = 2*pi*is/imsize;

clf
fig = figure;

if(1)
    for i = 1:numfiles
        subplot(2,2,1);
        imagesc(images(:,:,i));
        axis square;
        title(sprintf('t = %.3f\nSpace Domain',(i-1)*0.001))
        subplot(2,2,2);
        imagesc(fimages(:,:,i));
        title('Frequency Domain');
        axis square;
        subplot(2,2,[3 4])
        stem(freqs,radAves(:,i));
        minA = min(radAves(:,i));
        maxA = max(radAves(:,i));
        title('Fourier Transform');
        xlabel('Frequency (cycles/pixel)');
        ylabel('Amplitude');
        if(minA ~= maxA)
            axis([0 inf minA maxA]);
        else
            axis([0 inf minA inf]);
        end
        drawnow;
        fname = sprintf('./out/frame_%06d.png',i);
        saveas(fig, fname);
    end
end
disp('done');