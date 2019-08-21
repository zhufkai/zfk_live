# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

include_directories(${CMAKE_SOURCE_DIR}/../cpp/faac/include)
include_directories(${CMAKE_SOURCE_DIR}/../cpp/x264/include)
include_directories(${CMAKE_SOURCE_DIR}/../cpp/rtmpdump/include)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        live

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        queue.c
        zfk_live.c
        )

add_library(faac STATIC IMPORTED)
set_target_properties(faac PROPERTIES
        IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../cpp/faac/libfaac.a)

add_library(x264 STATIC IMPORTED)
set_target_properties(x264 PROPERTIES
        IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../cpp/x264/libx264.a)

add_library(rtmpdump STATIC IMPORTED)
set_target_properties(rtmpdump PROPERTIES
        IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../cpp/rtmpdump/librtmp.a)
# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        live
        faac
        x264
        rtmpdump
        android
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})