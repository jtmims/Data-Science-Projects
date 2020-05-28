# %% [code]
#importing dependencies and loading files from kaggle
from sklearn.model_selection import train_test_split
from tensorflow.python.keras.models import Sequential
from tensorflow.python.keras.layers import Dense, Flatten, Conv2D, MaxPool2D
from tensorflow.python.keras.utils.np_utils import to_categorical
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

import os
for dirname, _, filenames in os.walk('/kaggle/input'):
    for filename in filenames:
        print(os.path.join(dirname, filename))

# %% [code]
#setting up training and testing datasets
train_ds = pd.read_csv("/kaggle/input/digit-recognizer/train.csv")
test_ds = pd.read_csv("/kaggle/input/digit-recognizer/test.csv")

train_ds.shape #42000 rows 785 cols
test_ds.shape #28000 rows 784 cols

train_label = train_ds['label'].values
train_features = (train_ds.drop('label',axis=1)).values

#reshapping the images to be 28 by 28
train_features = train_features.reshape((42000,28,28,1))

#showing an image and making sure it is correct
#plt.imshow(train_features[0])
print(train_label[0])

#initializing and reshaping test features
test_features = test_ds.values
test_features = test_features.reshape(28000,28,28,1)

#normalizing data
train_features = train_features / 255
test_features = test_features / 255

# %% [code]
#test split and one-hot encoding
train_features, val_features, train_label, val_label = train_test_split(train_features, train_label, test_size=0.2, random_state=1)
train_label = to_categorical(train_label, 10)
val_label = to_categorical(val_label, 10)


# %% [code]
#building the model
model = Sequential()
model.add(Conv2D(filters=64,kernel_size=(3,3),input_shape=(28,28,1),activation='relu'))
model.add(Conv2D(filters=32,kernel_size=(3,3),input_shape=(28,28,1),activation='relu'))
model.add(Flatten())
model.add(Dense(10,activation='softmax'))
model.compile(loss='categorical_crossentropy',optimizer='adam' ,metrics=['accuracy'])

# %% [code]
result = model.fit(x=train_features,y=train_label,epochs=3,validation_data = (val_features,val_label))

# %% [code]
result.history
#98% accuracy
